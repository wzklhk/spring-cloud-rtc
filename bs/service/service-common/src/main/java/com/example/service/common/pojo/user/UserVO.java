package com.example.service.common.pojo.user;

import com.example.common.pojo.AbstractCommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzklhk
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends AbstractCommonVO {

    private String username;
}
