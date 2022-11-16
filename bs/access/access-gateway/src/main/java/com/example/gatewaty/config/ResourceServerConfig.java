package com.example.gatewaty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@EnableWebFluxSecurity
@Configuration
public class ResourceServerConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChan(ServerHttpSecurity http) {

        ReactiveAuthorizationManager manager = AuthenticatedReactiveAuthorizationManager.authenticated();
        http.authorizeExchange()
                .anyExchange().permitAll()  // 鉴权管理器配置
                .and().exceptionHandling()
//                .accessDeniedHandler()  // 处理未授权
//                .authenticationEntryPoint()  // 处理未认证
                .and()
                .csrf().disable();
        return http.build();

    }

}
