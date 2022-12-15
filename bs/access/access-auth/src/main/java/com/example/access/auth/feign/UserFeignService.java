package com.example.access.auth.feign;

import com.alibaba.fastjson.JSONObject;
import com.example.common.api.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wzklhk
 */
@FeignClient(value = "${feign.name.access-admin-service}")
public interface UserFeignService {

    @GetMapping("/user")
    ResultInfo<JSONObject> getUserByUsername(@RequestParam("username") String username);

}
