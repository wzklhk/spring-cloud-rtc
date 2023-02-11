package com.example.service.message.access.feign;

import com.example.common.pojo.CommonResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wzklhk
 */
@FeignClient(value = "${feign.name.access-admin-service}")
public interface AdminFeignService {

    @GetMapping("/user/getByToken")
    CommonResultInfo<Object> getUserByToken(@RequestParam("token") String token);

}
