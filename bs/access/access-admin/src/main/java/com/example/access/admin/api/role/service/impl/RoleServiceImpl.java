package com.example.access.admin.api.role.service.impl;

import com.example.access.admin.api.role.service.RoleService;
import com.example.access.admin.pojo.role.Role;
import com.example.access.admin.pojo.role.RoleVO;
import com.example.common.api.service.impl.AbstractCommonLogicDeleteServiceJpaImpl;
import org.springframework.stereotype.Service;

/**
 * @author wzklhk
 */
@Service
public class RoleServiceImpl extends AbstractCommonLogicDeleteServiceJpaImpl<RoleVO, Role, Long>
        implements RoleService {
}
