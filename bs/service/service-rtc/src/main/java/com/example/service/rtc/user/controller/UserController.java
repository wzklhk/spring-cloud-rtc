package com.example.service.rtc.user.controller;

import com.example.service.common.api.controller.CommonController;
import com.example.service.common.pojo.user.entity.UserDO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends CommonController<UserDO, UserDO, Integer> {
}