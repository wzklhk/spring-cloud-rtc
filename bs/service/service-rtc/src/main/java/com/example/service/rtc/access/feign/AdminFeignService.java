package com.example.service.rtc.access.feign;

import com.example.common.api.ResultInfo;
import com.example.service.common.pojo.user.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wzklhk
 */
@FeignClient(value = "${feign.name.access-admin-service}")
public interface AdminFeignService {

    @GetMapping("/user/getByToken")
    ResultInfo<UserVO> getUserByToken(@RequestParam("token") String token);

}
