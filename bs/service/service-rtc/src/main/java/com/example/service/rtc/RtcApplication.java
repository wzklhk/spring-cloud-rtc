package com.example.service.rtc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing  // 开启JPA自动填充
@ComponentScan(basePackages = "com.example")
@EntityScan(basePackages = "com.example")
@EnableDiscoveryClient  // 启动服务注册发现
@SpringBootApplication
public class RtcApplication {
    public static void main(String[] args) {
        SpringApplication.run(RtcApplication.class, args);
    }
}
