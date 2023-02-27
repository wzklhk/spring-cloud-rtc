package com.example.access.admin.api.role.service;

import com.example.access.admin.pojo.role.Role;
import com.example.access.admin.pojo.role.RoleVO;
import com.example.access.admin.pojo.user.UserVO;
import com.example.common.api.service.CommonService;

import java.util.List;

/**
 * @author wzklhk
 */
public interface RoleService extends CommonService<RoleVO, Role, Long> {
    List<UserVO> getUsersByRoleId(Long roleId);
}
