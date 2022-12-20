package com.example.access.auth.feign;

import com.alibaba.fastjson.JSONObject;
import com.example.common.pojo.CommonResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wzklhk
 */
@FeignClient(value = "${feign.name.access-admin-service}")
public interface UserFeignService {

    @GetMapping("/user/getByUsername")
    CommonResultInfo<JSONObject> getUserByUsername(@RequestParam("username") String username);

}
