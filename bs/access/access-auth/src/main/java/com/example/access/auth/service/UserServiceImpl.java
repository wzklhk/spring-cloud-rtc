package com.example.access.auth.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.access.auth.feign.UserFeignService;
import com.example.access.auth.pojo.user.UserDetailsImpl;
import com.example.common.api.PageCommon;
import com.example.common.api.ResultInfo;
import com.example.common.utils.CopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserFeignService userFeignService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JSONObject response = userFeignService.getUserByUsername(username);
        if (!(response.containsKey(ResultInfo.CODE) && response.getInteger(ResultInfo.CODE) == 0)) {
            if (response.containsKey(ResultInfo.MSG)) {
                String info = response.getString(ResultInfo.MSG);
                log.error(info);
                throw new UsernameNotFoundException(info);
            }
        }
        JSONArray list = response.getJSONObject(ResultInfo.DATA).getJSONArray(PageCommon.LIST);
        Object user = list.get(0);
        if (null == user) {
            throw new UsernameNotFoundException("账户不存在");
        }

        UserDetailsImpl userDetails = CopyUtil.copy(user, UserDetailsImpl.class);
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        return userDetails;
    }
}
