package com.example.service.common.pojo.message;

import com.example.common.pojo.AbstractCommonVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wzklhk
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MessageVO<T> extends AbstractCommonVO {

    private Long sendUserId;

    private Integer messageTypeValue;

    private String messageTypeName;

    private Long receiveUserId;

    private Long receiveRoomId;

    private T data;

    private Boolean isSent;


    public static <T> MessageVO<T> userMessage(Long sendUserId, Long receiveUserId, T data) {
        MessageType type = MessageType.USER_MESSAGE;
        return new MessageVO<>(sendUserId, type.getValue(), type.getName(), receiveUserId, null, data, false);
    }

    public static <T> MessageVO<T> roomMessage(Long sendUserId, Long roomId, T data) {
        MessageType type = MessageType.ROOM_MESSAGE;
        return new MessageVO<>(sendUserId, type.getValue(), type.getName(), null, roomId, data, false);
    }

    public static <T> MessageVO<T> broadcast(Long sendUserId, T data) {
        MessageType type = MessageType.BROADCAST;
        return new MessageVO<>(sendUserId, type.getValue(), type.getName(), null, null, data, false);
    }

    public static <T> MessageVO<T> userNotification(Long receiveUserId, T data) {
        MessageType type = MessageType.USER_NOTIFICATION;
        return new MessageVO<>(null, type.getValue(), type.getName(), receiveUserId, null, data, false);
    }

    public static <T> MessageVO<T> roomNotification(Long roomId, T data) {
        MessageType type = MessageType.ROOM_NOTIFICATION;
        return new MessageVO<>(null, type.getValue(), type.getName(), null, roomId, data, false);
    }
}
