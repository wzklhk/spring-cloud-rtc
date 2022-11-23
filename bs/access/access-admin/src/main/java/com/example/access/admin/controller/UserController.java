package com.example.access.admin.controller;


import com.example.access.admin.pojo.user.UserDO;
import com.example.common.api.controller.CommonController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends CommonController<UserDO, UserDO, Long> {
}
