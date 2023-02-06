package com.example.service.rtc.api.ws.service;

import com.alibaba.fastjson.JSON;
import com.example.common.pojo.CommonResultInfo;
import com.example.service.common.pojo.message.MessageVO;
import com.example.service.common.pojo.user.UserVO;
import com.example.service.rtc.access.AccessService;
import com.example.service.rtc.api.message.service.MessageService;
import com.example.service.rtc.api.room.service.impl.RoomServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 由于 spring 默认管理的是单例，所以只会注入一次 service
 * 不会给第二个 websocket 对象注入 service
 * 当新用户进入聊天时，系统又会创建一个新的 websocket 对象
 * 导致只要是用户连接创建的 websocket 对象，都不能再注入了
 * 解决方法：使用setter静态注入Service
 *
 * @author wzklhk
 */
@Slf4j
@ServerEndpoint(value = "/ws")
@Service
@Data
public class WebsocketService {


    /**
     * RoomService使用setter静态注入
     */
    private static RoomServiceImpl roomService;

    @Autowired
    public void setRoomService(RoomServiceImpl roomService) {
        WebsocketService.roomService = roomService;
    }

    private static AccessService accessService;

    @Autowired
    public void setAccessService(AccessService accessService) {
        WebsocketService.accessService = accessService;
    }

    private static MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        WebsocketService.messageService = messageService;
    }

    /**
     * 所有ws连接集合
     */
    private static Map<UserVO, WebsocketService> webSocketSessionMap = new ConcurrentHashMap<>();

    public static Map<UserVO, WebsocketService> getWebSocketSessionMap() {
        return webSocketSessionMap;
    }

    /**
     * 当前连接会话
     */
    private Session currentSession;

    /**
     * 当前用户
     */
    private UserVO currentUser;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
        if (!requestParameterMap.containsKey("token") || requestParameterMap.get("token").size() == 0) {
            throw new RuntimeException("websocket error: no param token");
        }
        this.currentUser = accessService.getUserByToken(requestParameterMap.get("token").get(0));
        this.currentSession = session;
        webSocketSessionMap.put(this.currentUser, this);
        log.info("有一连接打开，用户{}, 当前在线人数为：{}", this.currentUser, webSocketSessionMap.size());
        notifyMessage(null, this.currentUser + "已加入");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        webSocketSessionMap.remove(this.currentUser);
        log.info("有一连接关闭，移除{}的用户session, 当前在线人数为：{}", this.currentUser, webSocketSessionMap.size());
        notifyMessage(null, this.currentUser + "已断开");
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
            MessageVO messageVO = JSON.parseObject(messageJson, MessageVO.class);

            List<UserVO> receivers = messageVO.getReceivers();
            if (receivers != null && receivers.size() != 0) {
                if (receivers.size() == 1) {
                    unicastMessage(receivers.get(0), messageVO.getData());
                } else {
                    multicastMessage(receivers, messageVO.getData());
                }
            } else {
                broadcastMessage(messageVO.getData());
            }
        } catch (Exception e) {
            unicastMessage(this.currentUser, CommonResultInfo.error(e.toString()));
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
    private void sendMessage(String message) throws Exception {
        this.currentSession.getBasicRemote().sendText(message);
    }

    private void sendMessage(MessageVO message) throws IOException {
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("data", JSON.toJSONString(message));
        UserVO sender = message.getSender();
        if (sender != null) {
            messageMap.put("senderId", sender.getId());
        }
        if (currentUser != null) {
            messageMap.put("receiverId", currentUser.getId());
        }
        messageService.saveOrUpdate(messageMap);

        String s = JSON.toJSONString(message);
        this.currentSession.getBasicRemote().sendText(s);
    }

    /**
     * 服务端单播发送消息给所有客户端
     */
    public <T> void unicastMessage(UserVO receiver, T data) {
        log.info("单播消息：{}", data);
        if (webSocketSessionMap.containsKey(receiver)) {
            WebsocketService websocketService = webSocketSessionMap.get(receiver);
            try {
                MessageVO<T> message = MessageVO.unicast(currentUser, receiver, data);
                websocketService.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 服务端多播发送消息给所有客户端
     */
    public <T> void multicastMessage(List<UserVO> receivers, T data) {
        log.info("多播消息：{}", data);
        for (UserVO receiver : receivers) {
            if (webSocketSessionMap.containsKey(receiver)) {
                WebsocketService websocketService = webSocketSessionMap.get(receiver);
                try {
                    websocketService.sendMessage(MessageVO.multicast(currentUser, receivers, data));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 服务端广播发送消息给所有客户端
     */
    public <T> void broadcastMessage(T data) {
        log.info("广播消息：{}", data);
        for (Map.Entry<UserVO, WebsocketService> entry : webSocketSessionMap.entrySet()) {
            try {
                entry.getValue().sendMessage(MessageVO.broadcast(currentUser, data));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 服务端发送通知消息给客户端
     */
    public <T> void notifyMessage(List<UserVO> receivers, T data) {
        log.info("通知消息：{}", data);
        if (receivers != null) {
            for (Map.Entry<UserVO, WebsocketService> entry : webSocketSessionMap.entrySet()) {
                try {
                    entry.getValue().sendMessage(MessageVO.notification(receivers, data));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (Map.Entry<UserVO, WebsocketService> entry : webSocketSessionMap.entrySet()) {
                try {
                    entry.getValue().sendMessage(MessageVO.notification(null, data));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
