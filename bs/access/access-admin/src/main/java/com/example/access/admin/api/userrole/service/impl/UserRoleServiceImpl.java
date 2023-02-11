package com.example.access.admin.api.userrole.service.impl;

import com.example.access.admin.api.userrole.service.UserRoleService;
import com.example.access.admin.pojo.userrole.UserRole;
import com.example.access.admin.pojo.userrole.UserRoleVO;
import com.example.common.api.service.impl.AbstractCommonLogicDeleteServiceJpaImpl;
import org.springframework.stereotype.Service;

/**
 * @author wzklhk
 */
@Service
public class UserRoleServiceImpl extends AbstractCommonLogicDeleteServiceJpaImpl<UserRoleVO, UserRole, Long>
        implements UserRoleService {
}
