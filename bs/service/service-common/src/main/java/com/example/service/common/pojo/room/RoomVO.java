package com.example.service.common.pojo.room;

import com.example.common.pojo.AbstractCommonVO;
import com.example.service.common.pojo.user.UserVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzklhk
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoomVO extends AbstractCommonVO {
    private String name;

    private String description;

    private UserVO createUser;
}
