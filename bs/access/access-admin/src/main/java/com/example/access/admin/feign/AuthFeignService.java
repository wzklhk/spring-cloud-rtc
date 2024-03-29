package com.example.access.admin.feign;

import com.alibaba.fastjson.JSONObject;
import com.example.common.pojo.CommonResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wzklhk
 */
@FeignClient(value = "${feign.name.access-auth-service}")
public interface AuthFeignService {

    @GetMapping("/oauth/check_token")
    CommonResultInfo<JSONObject> checkToken(@RequestParam("token") String token);
}
