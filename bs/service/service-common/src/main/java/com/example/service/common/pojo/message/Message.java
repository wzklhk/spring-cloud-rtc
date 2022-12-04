package com.example.service.common.pojo.message;

import com.example.service.common.pojo.user.UserVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzklhk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message<T> {

    @ApiModelProperty(value = "发送方")
    private UserVO sender;

    @ApiModelProperty(value = "接收方列表")
    private List<UserVO> receivers;

    @ApiModelProperty(value = "要发送的数据")
    private T data;

    public static <T> Message<T> unicast(UserVO sender, UserVO receiver, T data) {
        List<UserVO> receivers = new ArrayList<>();
        receivers.add(receiver);
        return new Message<>(sender, receivers, data);
    }

    public static <T> Message<T> multicast(UserVO sender, List<UserVO> receivers, T data) {
        return new Message<>(sender, receivers, data);
    }

    public static <T> Message<T> broadcast(UserVO sender, T data) {
        return new Message<>(sender, null, data);
    }

    public static <T> Message<T> notification(T data) {
        return new Message<>(null, null, data);
    }
}
