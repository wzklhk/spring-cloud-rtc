package com.example.service.message.api.ws.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.example.service.common.pojo.message.MessageType;
import com.example.service.common.pojo.message.MessageVO;
import com.example.service.common.pojo.user.UserVO;
import com.example.service.message.access.AccessService;
import com.example.service.message.api.message.service.MessageService;
import com.example.service.message.api.room.service.impl.RoomServiceImpl;
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
public class WebSocketServiceImpl {


    /**
     * RoomService使用setter静态注入
     */
    private static RoomServiceImpl roomService;

    @Autowired
    public void setRoomService(RoomServiceImpl roomService) {
        WebSocketServiceImpl.roomService = roomService;
    }

    private static AccessService accessService;

    @Autowired
    public void setAccessService(AccessService accessService) {
        WebSocketServiceImpl.accessService = accessService;
    }

    private static MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        WebSocketServiceImpl.messageService = messageService;
    }

    /**
     * 所有ws连接集合
     */
    private static Map<Long, WebSocketServiceImpl> webSocketServiceMap = new ConcurrentHashMap<>();

    public static Map<Long, WebSocketServiceImpl> getWebSocketServiceMap() {
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
        notifyUserMessage(this.currentUser.getId(), "欢迎加入");

        MessageVO messageQueryVO = new MessageVO();
        messageQueryVO.setIsSent(false);
        messageQueryVO.setReceiveUserId(this.currentUser.getId());
        for (MessageVO message : messageService.getAll(messageQueryVO)) {
            try {
                sendAndUpdateMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        webSocketServiceMap.remove(this.currentUser.getId());
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
        try {
            MessageVO message = JSON.parseObject(messageJson, MessageVO.class);
            if (message.getMessageTypeValue() == null) {
                notifyUserMessage(this.currentUser.getId(), "Error: No messageTypeValue");
                return;
            }
            if (message.getMessageTypeValue().equals(MessageType.USER_MESSAGE.getValue())) {
                Long receiveUserId = message.getReceiveUserId();
                if (receiveUserId != null) {
                    sendAndSaveUserMessage(receiveUserId, message.getData());
                } else {
                    notifyUserMessage(this.currentUser.getId(), "Error: No receiveUserId");
                }

            } else if (message.getMessageTypeValue().equals(ROOM_MESSAGE.getValue())) {
                Long receiveRoomId = message.getReceiveRoomId();
                if (receiveRoomId != null) {
                    sendAndSaveRoomMessage(receiveRoomId, message.getData());
                } else {
                    notifyUserMessage(this.currentUser.getId(), "Error: No receiveRoomId");
                }
            }
            notifyUserMessage(this.currentUser.getId(), "OK");
        } catch (JSONException e) {
            e.printStackTrace();
            notifyUserMessage(this.currentUser.getId(), e.getMessage());
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("Websocket发生错误: {}", error.getMessage());
        notifyUserMessage(this.currentUser.getId(), error.getMessage());
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void send(String message) throws IOException {
        this.currentSession.getBasicRemote().sendText(message);
    }

    private <T> void sendMessage(MessageVO<T> message) throws IOException {
        send(JSON.toJSONString(message));
    }

    private <T> void sendAndUpdateMessage(MessageVO<T> message) throws IOException {
        send(JSON.toJSONString(message));

        if (message.getId() != null) {
            message.setIsSent(true);
            messageService.saveOrUpdate(message);
        }
    }

    /**
     * 服务端单播发送消息给所有客户端
     */
    public <T> void unicastMessage(Long receiverId, T data) {
        log.info("单播消息：{}", data);

        if (webSocketServiceMap.containsKey(receiverId)) {
            WebSocketServiceImpl websocketServiceImpl = webSocketServiceMap.get(receiverId);
            try {
                MessageVO<T> message = MessageVO.userMessage(this.currentUser.getId(), receiverId, data);
                websocketServiceImpl.send(JSON.toJSONString(message));
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
                WebSocketServiceImpl websocketServiceImpl = webSocketServiceMap.get(receiverId);
                try {
                    MessageVO<T> message = MessageVO.userMessage(this.currentUser.getId(), receiverId, data);
                    websocketServiceImpl.send(JSON.toJSONString(message));
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

        for (Map.Entry<Long, WebSocketServiceImpl> entry : webSocketServiceMap.entrySet()) {
            try {
                WebSocketServiceImpl websocketServiceImpl = entry.getValue();
                MessageVO<T> message = MessageVO.broadcast(this.currentUser.getId(), data);
                websocketServiceImpl.send(JSON.toJSONString(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> void sendAndSaveUserMessage(Long receiveUserId, T data) {
        MessageVO<T> message = MessageVO.userMessage(this.currentUser.getId(), receiveUserId, data);

        MessageVO sentMessage = messageService.saveOrUpdate(message);
        if (webSocketServiceMap.containsKey(receiveUserId)) {
            WebSocketServiceImpl websocketServiceImpl = webSocketServiceMap.get(receiveUserId);
            try {
                websocketServiceImpl.sendAndUpdateMessage(sentMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public <T> void sendAndSaveRoomMessage(Long receiveRoomId, T data) {
        MessageVO<T> message = MessageVO.roomMessage(this.currentUser.getId(), receiveRoomId, data);

        List<Long> userIds = roomService.getUserIdsByRoomId(receiveRoomId);
        for (Long userId : userIds) {
            message.setReceiveUserId(userId);
            MessageVO sentMessage = messageService.saveOrUpdate(message);
            if (webSocketServiceMap.containsKey(userId)) {
                WebSocketServiceImpl websocketServiceImpl = webSocketServiceMap.get(userId);
                try {
                    websocketServiceImpl.sendAndUpdateMessage(sentMessage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public <T> void notifyUserMessage(Long receiveUserId, T data) {
        MessageVO<T> message = MessageVO.userNotification(receiveUserId, data);
        if (webSocketServiceMap.containsKey(receiveUserId)) {
            WebSocketServiceImpl websocketServiceImpl = webSocketServiceMap.get(receiveUserId);
            try {
                websocketServiceImpl.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> void notifyAndSaveUserMessage(Long receiveUserId, T data) {
        MessageVO<T> message = MessageVO.userNotification(receiveUserId, data);
        MessageVO sentMessage = messageService.saveOrUpdate(message);
        if (webSocketServiceMap.containsKey(receiveUserId)) {
            WebSocketServiceImpl websocketServiceImpl = webSocketServiceMap.get(receiveUserId);
            try {
                websocketServiceImpl.sendAndUpdateMessage(sentMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> void notifyRoomMessage(Long roomId, T data) {
        MessageVO<T> message = MessageVO.roomNotification(roomId, data);

        List<Long> userIds = roomService.getUserIdsByRoomId(roomId);
        for (Long userId : userIds) {
            if (webSocketServiceMap.containsKey(userId)) {
                WebSocketServiceImpl websocketServiceImpl = webSocketServiceMap.get(userId);
                try {
                    websocketServiceImpl.sendMessage(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public <T> void notifyAndSaveRoomMessage(Long receiveRoomId, T data) {
        MessageVO<T> message = MessageVO.roomNotification(receiveRoomId, data);
        List<Long> userIds = roomService.getUserIdsByRoomId(receiveRoomId);

        for (Long userId : userIds) {
            message.setReceiveUserId(userId);
            MessageVO sentMessage = messageService.saveOrUpdate(message);
            if (webSocketServiceMap.containsKey(userId)) {
                WebSocketServiceImpl websocketServiceImpl = webSocketServiceMap.get(userId);
                try {
                    websocketServiceImpl.sendAndUpdateMessage(sentMessage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
