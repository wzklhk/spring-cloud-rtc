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

    private Long receiveUserId;

    private Long receiveRoomId;

    private T data;

    private Boolean isSent;


    public static <T> MessageVO<T> userMessage(Long sendUserId, Long receiveUserId, T data) {
        return new MessageVO<>(sendUserId, MessageType.USER_MESSAGE.getValue(), receiveUserId, null, data, false);
    }

    public static <T> MessageVO<T> roomMessage(Long sendUserId, Long roomId, T data) {
        return new MessageVO<>(sendUserId, MessageType.ROOM_MESSAGE.getValue(), null, roomId, data, false);
    }

    public static <T> MessageVO<T> broadcast(Long sendUserId, T data) {
        return new MessageVO<>(sendUserId, MessageType.BROADCAST.getValue(), null, null, data, false);
    }

    public static <T> MessageVO<T> userNotification(Long receiveUserId, T data) {
        return new MessageVO<>(null, MessageType.USER_NOTIFICATION.getValue(), receiveUserId, null, data, false);
    }

    public static <T> MessageVO<T> roomNotification(Long roomId, T data) {
        return new MessageVO<>(null, MessageType.ROOM_NOTIFICATION.getValue(), null, roomId, data, false);
    }
}
