package com.example.access.admin.service.impl;

import com.example.access.admin.pojo.user.UserDO;
import com.example.access.admin.service.UserService;
import com.example.common.api.service.impl.CommonServiceJpaImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends CommonServiceJpaImpl<UserDO, UserDO, Long>
        implements UserService {
}
