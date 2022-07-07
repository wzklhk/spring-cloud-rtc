package com.example.rtc.user.controller;

import com.example.common.api.controller.CommonController;
import com.example.common.pojo.user.entity.UserDO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends CommonController<UserDO, UserDO, Integer> {
}
