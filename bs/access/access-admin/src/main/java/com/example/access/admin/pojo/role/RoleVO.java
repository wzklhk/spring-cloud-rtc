package com.example.access.admin.pojo.role;

import com.example.common.pojo.AbstractCommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzklhk
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleVO extends AbstractCommonVO {
    private String name;

    private String description;
}
