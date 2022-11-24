package com.example.access.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "access-admin")
public interface UserFeignService {

    @GetMapping("/user")
    Object getUserByUsername(@RequestParam("username") String username);

}
