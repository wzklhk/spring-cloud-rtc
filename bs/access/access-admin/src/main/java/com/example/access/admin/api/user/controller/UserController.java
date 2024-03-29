package com.example.access.admin.api.user.controller;

import com.example.access.admin.api.user.service.UserService;
import com.example.access.admin.pojo.role.RoleVO;
import com.example.access.admin.pojo.user.User;
import com.example.access.admin.pojo.user.UserVO;
import com.example.common.api.controller.CommonController;
import com.example.common.pojo.CommonResultInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wzklhk
 */
@RestController
@RequestMapping("/user")
public class UserController extends CommonController<UserVO, User, Long> {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getByToken")
    public CommonResultInfo<UserVO> getByToken(@RequestParam String token) {
        return CommonResultInfo.ok(userService.getByToken(token));
    }

    @GetMapping("/getByUsername")
    public CommonResultInfo<UserVO> getByUsername(@RequestParam String username) {
        UserVO userVO = userService.getByUsername(username);
        if (userVO != null) {
            return CommonResultInfo.ok(userVO);
        } else {
            return CommonResultInfo.error();
        }
    }

    @GetMapping("/getRolesByUserId")
    public CommonResultInfo<List<RoleVO>> getRolesByUserId(Long userId) {
        return CommonResultInfo.ok(userService.getRolesByUserId(userId));
    }
}
