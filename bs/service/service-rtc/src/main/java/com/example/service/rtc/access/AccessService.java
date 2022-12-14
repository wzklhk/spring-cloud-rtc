package com.example.service.rtc.access;

import com.alibaba.fastjson.JSONObject;
import com.example.common.api.ErrorCodeEnum;
import com.example.common.api.ResultInfo;
import com.example.common.utils.CopyUtil;
import com.example.service.common.pojo.user.UserVO;
import com.example.service.rtc.access.feign.AdminFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wzklhk
 */
@Slf4j
@Service
public class AccessService {
    private final AdminFeignService adminFeignService;

    public AccessService(AdminFeignService adminFeignService) {
        this.adminFeignService = adminFeignService;
    }

    public UserVO getUserByToken(String token) {
        ResultInfo<JSONObject> res = adminFeignService.getUserByToken(token);
        if (!res.getCode().equals(ErrorCodeEnum.OK.getErrorCode())) {
            log.info(res.getMsg());
            log.info(res.getData().toString());
            return null;
        }
        JSONObject data = res.getData();
        return CopyUtil.copy(data, UserVO.class);
    }

}
