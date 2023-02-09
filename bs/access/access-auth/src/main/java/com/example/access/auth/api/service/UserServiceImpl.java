package com.example.access.auth.api.service;

import com.example.access.auth.feign.UserFeignService;
import com.example.access.auth.pojo.user.UserDetailsImpl;
import com.example.common.pojo.CommonResultInfo;
import com.example.common.pojo.ErrorCodeEnum;
import com.example.common.utils.CopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author wzklhk
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final UserFeignService userFeignService;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserFeignService userFeignService) {
        this.passwordEncoder = passwordEncoder;
        this.userFeignService = userFeignService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CommonResultInfo<Object> res = userFeignService.getUserByUsername(username);
        if (!res.getCode().equals(ErrorCodeEnum.OK.getErrorCode())) {
            String errorInfo = res.getMsg();
            log.error(errorInfo);
            throw new UsernameNotFoundException(errorInfo);
        }

        Object user = res.getData();

        if (null == user) {
            throw new UsernameNotFoundException("账户不存在");
        }

        UserDetailsImpl userDetails = CopyUtil.copy(user, UserDetailsImpl.class);
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        return userDetails;
    }
}
