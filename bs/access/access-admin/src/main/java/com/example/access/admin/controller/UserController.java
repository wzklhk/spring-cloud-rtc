package com.example.access.admin.controller;


import com.example.access.admin.pojo.user.UserDO;
import com.example.access.admin.pojo.user.UserVO;
import com.example.access.admin.service.UserService;
import com.example.common.api.ResultInfo;
import com.example.common.api.controller.CommonController;
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
    public ResultInfo<UserVO> getByToken(@RequestParam String token) {
        return ResultInfo.ok(userService.getByToken(token));
    }
}
