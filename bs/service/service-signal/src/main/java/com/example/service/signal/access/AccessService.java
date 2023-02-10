package com.example.service.signal.access;

import com.example.common.pojo.CommonResultInfo;
import com.example.common.pojo.ErrorCodeEnum;
import com.example.common.utils.CopyUtil;
import com.example.service.common.pojo.user.UserVO;
import com.example.service.signal.access.feign.AdminFeignService;
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
        CommonResultInfo<Object> res = adminFeignService.getUserByToken(token);
        if (!res.getCode().equals(ErrorCodeEnum.OK.getErrorCode())) {
            log.info(res.getMsg());
            log.info(res.getData().toString());
            return null;
        }
        return CopyUtil.copy(res.getData(), UserVO.class);
    }

}
