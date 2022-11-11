package com.example.service.rtc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // 开启JPA自动填充
@ComponentScan(basePackages = "com.example.service")
@EntityScan(basePackages = "com.example.service")
public class RtcApplication {
    public static void main(String[] args) {
        SpringApplication.run(RtcApplication.class, args);
    }
}
