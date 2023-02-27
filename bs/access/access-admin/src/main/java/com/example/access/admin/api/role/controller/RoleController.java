package com.example.access.admin.api.role.controller;

import com.example.access.admin.api.role.service.RoleService;
import com.example.access.admin.pojo.role.Role;
import com.example.access.admin.pojo.role.RoleVO;
import com.example.access.admin.pojo.user.UserVO;
import com.example.common.api.controller.CommonController;
import com.example.common.pojo.CommonResultInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wzklhk
 */
@RestController
@RequestMapping("/role")
public class RoleController extends CommonController<RoleVO, Role, Long> {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getUsersByRoleId")
    public CommonResultInfo<List<UserVO>> getUsersByRoleId(Long roleId) {
        return CommonResultInfo.ok(roleService.getUsersByRoleId(roleId));
    }
}
