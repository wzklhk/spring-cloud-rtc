package com.example.service.rtc.access;

import com.example.common.api.ResultInfo;
import com.example.service.common.pojo.user.UserVO;
import com.example.service.rtc.access.feign.AdminFeignService;
import org.springframework.stereotype.Service;

/**
 * @author wzklhk
 */
@Service
public class AccessService {
    private final AdminFeignService adminFeignService;

    public AccessService(AdminFeignService adminFeignService) {
        this.adminFeignService = adminFeignService;
    }

    public UserVO getUserByToken(String token) {
        ResultInfo<UserVO> result = adminFeignService.getUserByToken(token);
        return result.getData();
    }

}
