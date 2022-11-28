package com.example.access.auth;

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
@EnableFeignClients
@ComponentScan(basePackages = "com.example")
@EntityScan(basePackages = "com.example")
@EnableDiscoveryClient
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
