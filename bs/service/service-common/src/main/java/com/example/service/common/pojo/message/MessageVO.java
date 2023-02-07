package com.example.service.common.pojo.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wzklhk
 */
@Data
@AllArgsConstructor
public class MessageVO<T> {

    private Long senderId;

    private Integer messageTypeValue;

    private Long receiverId;

    private T data;


    public static <T> MessageVO<T> userMessage(Long sendUserId, Long receiveUserId, T data) {
        return new MessageVO<>(sendUserId, MessageType.USER_MESSAGE.getValue(), receiveUserId, data);
    }

    public static <T> MessageVO<T> roomMessage(Long senderId, Long roomId, T data) {
        return new MessageVO<>(senderId, MessageType.ROOM_MESSAGE.getValue(), roomId, data);
    }

    public static <T> MessageVO<T> broadcast(Long senderId, T data) {
        return new MessageVO<>(senderId, MessageType.BROADCAST.getValue(), null, data);
    }

    public static <T> MessageVO<T> notification(Long receiverId, T data) {
        return new MessageVO<>(null, MessageType.NOTIFICATION.getValue(), receiverId, data);
    }
}
