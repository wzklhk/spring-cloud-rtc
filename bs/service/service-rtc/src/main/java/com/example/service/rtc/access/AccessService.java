package com.example.service.rtc.access;

import com.example.common.api.ResultInfo;
import com.example.service.common.pojo.user.UserVO;
import com.example.service.rtc.access.feign.AdminFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wzklhk
 */
@Service
public class AccessService {
    @Autowired
    private AdminFeignService adminFeignService;

    public UserVO getUserByToken(String token) {
        ResultInfo<UserVO> result = adminFeignService.getUserByToken(token);
        return result.getData();
    }

}
