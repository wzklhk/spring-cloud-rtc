package com.example.access.admin.api.role.controller;

import com.example.access.admin.pojo.role.Role;
import com.example.access.admin.pojo.role.RoleVO;
import com.example.common.api.controller.CommonController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wzklhk
 */
@RestController
@RequestMapping("/role")
public class RoleController extends CommonController<RoleVO, Role, Long> {
}
