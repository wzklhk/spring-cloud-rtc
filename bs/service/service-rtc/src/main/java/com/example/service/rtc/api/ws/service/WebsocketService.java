package com.example.service.rtc.api.ws.service;

import com.alibaba.fastjson.JSON;
import com.example.service.common.pojo.message.MessageType;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.service.common.pojo.message.MessageType.ROOM_MESSAGE;

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
    private static Map<Long, WebsocketService> webSocketServiceMap = new ConcurrentHashMap<>();

    public static Map<Long, WebsocketService> getWebSocketServiceMap() {
        return webSocketServiceMap;
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
            throw new RuntimeException("No param token. ");
        }
        String token = requestParameterMap.get("token").get(0);
        this.currentUser = accessService.getUserByToken(token);
        if (this.currentUser == null || this.currentUser.getId() == null) {
            throw new RuntimeException("Cannot get user by given token. ");
        }
        this.currentSession = session;
        webSocketServiceMap.put(this.currentUser.getId(), this);
        log.info("有一连接打开，用户{}, 当前在线人数为：{}", this.currentUser, webSocketServiceMap.size());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        webSocketServiceMap.remove(this.currentUser);
        log.info("有一连接关闭，移除{}的户, 当前在线人数为：{}", this.currentUser, webSocketServiceMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     *
     * @param messageJson 客户端发送过来的消息
     *                    {"data": {"command": "offer"},"messageType": 1,"receiverId": 1}
     *                    sender: 发送方用户
     *                    receiverId: 接收方用户ID/房间ID
     */
    @OnMessage
    public void onMessage(String messageJson, Session session) {
        log.info("收到{}的消息：{}", session, messageJson);
        MessageVO message = JSON.parseObject(messageJson, MessageVO.class);

        if (message.getMessageTypeValue().equals(MessageType.USER_MESSAGE.getValue())) {
            sendUserMessage(message.getReceiverId(), message.getData());
        } else if (message.getMessageTypeValue().equals(ROOM_MESSAGE.getValue())) {
            sendRoomMessage(message.getReceiverId(), message.getData());
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("Websocket发生错误: {}", error.getMessage());
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message) throws IOException {
        this.currentSession.getBasicRemote().sendText(message);
    }

    private void sendMessage(MessageVO message) throws IOException {
        /*Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("data", JSON.toJSONString(message));
        Long senderId = message.getSenderId();
        if (senderId != null) {
            messageMap.put("senderId", senderId);
        }
        messageMap.put("isRoomMsg", false);
        if (currentUser != null) {
            messageMap.put("receiverId", currentUser.getId());
        }
        messageService.saveOrUpdate(messageMap);*/
        String s = JSON.toJSONString(message);
        this.currentSession.getBasicRemote().sendText(s);
    }

    /**
     * 服务端单播发送消息给所有客户端
     */
    public <T> void unicastMessage(Long receiverId, T data) {
        log.info("单播消息：{}", data);
        if (webSocketServiceMap.containsKey(receiverId)) {
            WebsocketService websocketService = webSocketServiceMap.get(receiverId);
            try {
                MessageVO<T> message = MessageVO.userMessage(currentUser.getId(), receiverId, data);
                websocketService.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 服务端多播发送消息给所有客户端
     */
    public <T> void multicastMessage(List<Long> receiverIds, T data) {
        log.info("多播消息：{}", data);
        for (Long receiverId : receiverIds) {
            if (webSocketServiceMap.containsKey(receiverId)) {
                WebsocketService websocketService = webSocketServiceMap.get(receiverId);
                try {
                    websocketService.sendMessage(MessageVO.userMessage(this.currentUser.getId(), receiverId, data));
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
        for (Map.Entry<Long, WebsocketService> entry : webSocketServiceMap.entrySet()) {
            try {
                entry.getValue().sendMessage(MessageVO.broadcast(this.currentUser.getId(), data));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> void sendUserMessage(Long receiveUserId, T data) {
        MessageVO<T> message = MessageVO.userMessage(this.currentUser.getId(), receiveUserId, data);
        if (webSocketServiceMap.containsKey(receiveUserId)) {
            WebsocketService websocketService = webSocketServiceMap.get(receiveUserId);
            try {
                websocketService.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> void sendRoomMessage(Long receiveRoomId, T data) {
        MessageVO<T> message = MessageVO.roomMessage(this.currentUser.getId(), receiveRoomId, data);
        List<Long> userIds = roomService.getUserIdsByRoomId(receiveRoomId);
        for (Long userId : userIds) {
            if (webSocketServiceMap.containsKey(userId)) {
                WebsocketService websocketService = webSocketServiceMap.get(userId);
                try {

                    websocketService.sendMessage(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
