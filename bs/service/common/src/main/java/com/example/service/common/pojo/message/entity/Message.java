package com.example.service.common.pojo.message.entity;

import com.example.service.common.pojo.user.entity.UserDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message<T> {

    @ApiModelProperty(value = "发送方")
    private UserDO sender;

    @ApiModelProperty(value = "接收方列表")
    private List<UserDO> receivers;

    @ApiModelProperty(value = "要发送的数据")
    private T data;

    public static <T> Message<T> unicast(UserDO sender, UserDO receiver, T data) {
        List<UserDO> receivers = new ArrayList<>();
        receivers.add(receiver);
        return new Message<>(sender, receivers, data);
    }

    public static <T> Message<T> multicast(UserDO sender, List<UserDO> receivers, T data) {
        return new Message<>(sender, receivers, data);
    }

    public static <T> Message<T> broadcast(UserDO sender, T data) {
        return new Message<>(sender, null, data);
    }

    public static <T> Message<T> notification(T data) {
        return new Message<>(null, null, data);
    }
}
