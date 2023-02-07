package com.example.service.common.pojo.message;

/**
 * @author wzklhk
 */

public enum MessageType {
    USER_MESSAGE(0, "user message"),
    ROOM_MESSAGE(1, "room message"),
    BROADCAST(2, "broadcast"),
    NOTIFICATION(3, "notification");

    private final Integer value;

    private final String name;

    MessageType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
