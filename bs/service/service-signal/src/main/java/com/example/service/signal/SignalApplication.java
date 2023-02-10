package com.example.service.signal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 注解@EnableJpaAuditing  // 开启JPA自动填充
 * <p>
 * 注解@EnableDiscoveryClient  // 启动服务注册发现
 *
 * @author wzklhk
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableJpaAuditing
@ComponentScan(basePackages = "com.example")
@EntityScan(basePackages = "com.example")
@SpringBootApplication
public class SignalApplication {
    public static void main(String[] args) {
        SpringApplication.run(SignalApplication.class, args);
    }
}
