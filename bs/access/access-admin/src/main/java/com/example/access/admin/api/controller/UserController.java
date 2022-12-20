package com.example.access.admin.api.controller;


import com.example.access.admin.api.service.UserService;
import com.example.access.admin.pojo.user.UserDO;
import com.example.access.admin.pojo.user.UserVO;
import com.example.common.api.controller.CommonController;
import com.example.common.pojo.CommonResultInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wzklhk
 */
@RestController
@RequestMapping("/user")
public class UserController extends CommonController<UserVO, UserDO, Long> {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getByToken")
    public CommonResultInfo<UserVO> getByToken(@RequestParam String token) {
        return CommonResultInfo.ok(userService.getByToken(token));
    }
}
