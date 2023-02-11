package com.example.access.admin.pojo.userrole;

import com.example.common.pojo.AbstractCommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzklhk
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRoleVO extends AbstractCommonVO {

    private Long userId;

    private Long roleId;

}
