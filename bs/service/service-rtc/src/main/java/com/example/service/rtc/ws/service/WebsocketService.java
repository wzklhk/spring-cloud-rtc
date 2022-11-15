package com.example.service.rtc.ws.service;

import com.alibaba.fastjson.JSON;
import com.example.common.api.ResultInfo;
import com.example.service.common.pojo.message.entity.Message;
import com.example.service.common.pojo.user.entity.UserDO;
import com.example.service.rtc.room.service.RoomService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 由于 spring 默认管理的是单例，所以只会注入一次 service
 * 不会给第二个 websocket 对象注入 service
 * 当新用户进入聊天时，系统又会创建一个新的 websocket 对象
 * 导致只要是用户连接创建的 websocket 对象，都不能再注入了
 * 解决方法：使用setter静态注入Service
 */
@Slf4j
@ServerEndpoint(value = "/ws")
@Service
@Data
public class WebsocketService {


    private static RoomService roomService;  // 使用setter静态注入

    @Autowired
    public void setRoomService(RoomService roomService) {
        WebsocketService.roomService = roomService;
    }

    /**
     * 总连接数
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 所有ws连接集合
     */
    private static Set<WebsocketService> webSocketSet = new CopyOnWriteArraySet<>();

    public static Set<WebsocketService> getWebSocketSet() {
        return webSocketSet;
    }

    /**
     * 当前连接会话
     */
    private Session currentSession;

    /**
     * 当前用户
     */
    private UserDO currentUser;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
        if (!requestParameterMap.containsKey("username") || requestParameterMap.get("username").size() == 0) {
            throw new RuntimeException("websocket error: no param username");
        }
        String username = requestParameterMap.get("username").get(0);

        UserDO userDO = new UserDO();
        userDO.setUsername(username);
        this.currentUser = userDO;
        this.currentSession = session;
        webSocketSet.add(this);
        onlineCount.getAndIncrement();
        log.info("有一连接打开，用户username={}, 当前在线人数为：{}", username, webSocketSet.size());
        broadcastMessage(this.currentUser + "已加入");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        webSocketSet.remove(this);
        onlineCount.getAndDecrement();
        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", this.currentUser, webSocketSet.size());
        broadcastMessage(this.currentUser + "已断开");
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     *
     * @param messageJson 客户端发送过来的消息
     *                    {"data": {"command": "offer"},"receivers": [{"username": "user2"},{"username": "user3"},{"username": "user4"}]}
     */
    @OnMessage
    public void onMessage(String messageJson, Session session) {
        log.info("收到{}的消息：{}", session, messageJson);
        try {
            Message message = JSON.parseObject(messageJson, Message.class);

            if (message.getReceivers() != null) {
                multicastMessage(message.getReceivers(), message.getData());
            } else {
                broadcastMessage(message.getData());
            }
        } catch (Exception e) {
            unicastMessage(this.currentUser, ResultInfo.error(e.toString()));
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message) throws IOException {
        this.currentSession.getBasicRemote().sendText(message);
    }

    /**
     * 服务端单播发送消息给所有客户端
     */
    public <T> void unicastMessage(UserDO receiver, T data) {
        log.info("单播消息：{}", data);
        // TODO 优化搜索算法
        for (WebsocketService item : webSocketSet) {
            if (receiver.equals(item.currentUser)) {
                try {
                    item.sendMessage(JSON.toJSONString(Message.unicast(currentUser, receiver, data)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 服务端多播发送消息给所有客户端
     */
    public <T> void multicastMessage(List<UserDO> receivers, T data) {
        log.info("多播消息：{}", data);
        for (UserDO receiver : receivers) {
            unicastMessage(receiver, data);
        }
    }

    /**
     * 服务端广播发送消息给所有客户端
     */
    public <T> void broadcastMessage(T data) {
        log.info("广播消息：{}", data);
        for (WebsocketService item : webSocketSet) {
            try {
                item.sendMessage(JSON.toJSONString(Message.broadcast(currentUser, data)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 服务端发送通知消息给所有客户端
     */
    public <T> void notifyMessage(T data) {
        log.info("广播消息：{}", data);
        for (WebsocketService item : webSocketSet) {
            try {
                item.sendMessage(JSON.toJSONString(Message.broadcast(currentUser, data)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
