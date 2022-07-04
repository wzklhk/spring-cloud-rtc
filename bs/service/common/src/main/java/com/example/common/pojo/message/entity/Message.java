package com.example.common.pojo.message.entity;

import com.example.common.pojo.user.entity.UserDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message<T> {

    @ApiModelProperty(value = "发送方")
    private UserDO sendUser;

    @ApiModelProperty(value = "接收方列表")
    private UserDO receiveUser;

    @ApiModelProperty(value = "接收方列表")
    private List<UserDO> receiveUsers;

    @ApiModelProperty(value = "要发送的数据")
    private T data;

    public static <T> Message<T> unicast(UserDO sendUser, UserDO receiveUser, T data) {
        return new Message<>(sendUser, receiveUser, null, data);
    }

    public static <T> Message<T> multicast(UserDO sendUser, List<UserDO> receiveUsers, T data) {
        return new Message<>(sendUser, null, receiveUsers, data);
    }

    public static <T> Message<T> broadcast(UserDO sendUser, T data) {
        return new Message<>(sendUser, null, null, data);
    }
}
