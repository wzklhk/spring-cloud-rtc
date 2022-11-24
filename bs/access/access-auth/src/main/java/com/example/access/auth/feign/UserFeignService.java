package com.example.access.auth.feign;

import com.example.common.api.PageCommon;
import com.example.common.api.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "access-admin")
public interface UserFeignService {

    @GetMapping("/user")
    ResultInfo<PageCommon> getUserByUsername(@RequestParam("username") String username);

}
