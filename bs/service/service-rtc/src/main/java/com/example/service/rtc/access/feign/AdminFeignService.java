package com.example.service.rtc.access.feign;

import com.alibaba.fastjson.JSONObject;
import com.example.common.api.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wzklhk
 */
@FeignClient(value = "${feign.name.access-admin-service}")
public interface AdminFeignService {

    @GetMapping("/user/getByToken")
    ResultInfo<JSONObject> getUserByToken(@RequestParam("token") String token);

}
