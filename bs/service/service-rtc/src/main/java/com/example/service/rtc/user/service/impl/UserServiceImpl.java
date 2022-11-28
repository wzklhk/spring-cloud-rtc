package com.example.service.rtc.user.service.impl;

import com.example.common.api.service.impl.CommonServiceJpaImpl;
import com.example.service.common.pojo.user.entity.UserDO;
import com.example.service.rtc.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author wzklhk
 */
@Service
public class UserServiceImpl extends CommonServiceJpaImpl<UserDO, UserDO, Integer>
        implements UserService {
}
