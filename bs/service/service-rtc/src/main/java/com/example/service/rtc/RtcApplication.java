package com.example.service.rtc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 注解@EnableJpaAuditing  // 开启JPA自动填充
 * <p>
 * 注解@EnableDiscoveryClient  // 启动服务注册发现
 *
 * @author wzklhk
 */
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = "com.example")
@EntityScan(basePackages = "com.example")
@SpringBootApplication
public class RtcApplication {
    public static void main(String[] args) {
        SpringApplication.run(RtcApplication.class, args);
    }

}
