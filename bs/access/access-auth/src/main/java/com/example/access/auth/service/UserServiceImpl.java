package com.example.access.auth.service;

import com.example.access.auth.feign.UserFeignService;
import com.example.access.auth.pojo.user.UserDetailsImpl;
import com.example.common.api.PageCommon;
import com.example.common.api.ResultInfo;
import com.example.common.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserFeignService userFeignService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResultInfo<PageCommon> result = userFeignService.getUserByUsername(username);
        Object user = result.getData().getList().get(0);
        if (null == user) {
            throw new UsernameNotFoundException("账户不存在");
        }

        UserDetailsImpl userDetails = CopyUtil.copy(user, UserDetailsImpl.class);
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        return userDetails;
    }
}
