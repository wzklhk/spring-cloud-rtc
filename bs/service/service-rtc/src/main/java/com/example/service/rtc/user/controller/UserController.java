package com.example.service.rtc.user.controller;

import com.example.common.api.controller.CommonController;
import com.example.service.common.pojo.user.entity.UserDO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wzklhk
 */
@RestController
@RequestMapping("/user")
public class UserController extends CommonController<UserDO, UserDO, Integer> {
}
