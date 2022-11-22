package com.example.access.auth.controller;


import com.example.access.auth.pojo.user.UserDO;
import com.example.common.api.controller.CommonController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends CommonController<UserDO, UserDO, Long> {
}
