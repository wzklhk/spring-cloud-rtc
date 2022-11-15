package com.example.gatewaty.config;

import com.example.gatewaty.authorzation.AuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@EnableWebFluxSecurity
@Configuration
public class ResourceServerConfig {

    @Autowired
    private AuthorizationManager authorizationManager;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChan(ServerHttpSecurity http) {
        http.authorizeExchange()
                .anyExchange().access(authorizationManager)  // 鉴权管理器配置
                .and()
                .exceptionHandling()
                .and()
                .csrf().disable();
        return http.build();

    }

}
