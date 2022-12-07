package com.example.access.auth.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.access.auth.feign.UserFeignService;
import com.example.access.auth.pojo.user.UserDetailsImpl;
import com.example.common.api.ErrorCodeEnum;
import com.example.common.api.PageCommon;
import com.example.common.api.ResultInfo;
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
        JSONObject response = userFeignService.getUserByUsername(username);
        if (!(response.containsKey(ResultInfo.CODE) && response.getInteger(ResultInfo.CODE) == ErrorCodeEnum.OK.getErrorCode())) {
            if (response.containsKey(ResultInfo.MSG)) {
                String info = response.getString(ResultInfo.MSG);
                log.error(info);
                throw new UsernameNotFoundException(info);
            }
        }
        JSONArray list = response.getJSONObject(ResultInfo.DATA).getJSONArray(PageCommon.LIST);

        if (null == list || 0 == list.size()) {
            throw new UsernameNotFoundException("账户不存在");
        }
        Object user = list.get(0);

        UserDetailsImpl userDetails = CopyUtil.copy(user, UserDetailsImpl.class);
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        return userDetails;
    }
}
