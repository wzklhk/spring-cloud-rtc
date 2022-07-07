package com.example.rtc.user.service.impl;

import com.example.common.api.service.impl.CommonServiceJpaImpl;
import com.example.common.pojo.user.entity.UserDO;
import com.example.rtc.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends CommonServiceJpaImpl<UserDO, UserDO, Integer>
        implements UserService {
}
