package com.example.access.admin.pojo.user;

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

    private String password;

    private String nickname;

    private String description;

    private Boolean isEnabled;

    private Boolean isLocked;
}
