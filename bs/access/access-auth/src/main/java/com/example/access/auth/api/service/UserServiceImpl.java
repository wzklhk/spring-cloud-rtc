package com.example.access.auth.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.access.auth.feign.UserFeignService;
import com.example.access.auth.pojo.user.UserDetailsImpl;
import com.example.common.pojo.CommonPageInfo;
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
        CommonResultInfo<JSONObject> res = userFeignService.getUserByUsername(username);
        if (!res.getCode().equals(ErrorCodeEnum.OK.getErrorCode())) {
            String errorInfo = res.getMsg();
            log.error(errorInfo);
            throw new UsernameNotFoundException(errorInfo);
        }

        JSONArray list = res.getData().getJSONArray(CommonPageInfo.LIST);

        if (null == list || 0 == list.size()) {
            throw new UsernameNotFoundException("账户不存在");
        }

        Object user = list.get(0);

        UserDetailsImpl userDetails = CopyUtil.copy(user, UserDetailsImpl.class);
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        return userDetails;
    }
}
