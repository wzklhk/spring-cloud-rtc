package com.example.gatewaty.config;

import com.example.gatewaty.component.RestfulAccessDeniedHandler;
import com.example.gatewaty.component.RestfulAuthenticafultionEntryPoint;
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
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestfulAuthenticafultionEntryPoint restfulAuthenticafultionEntryPoint;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChan(ServerHttpSecurity http) {

        http.authorizeExchange()
//                .anyExchange().permitAll()  // 鉴权管理器配置
                .anyExchange().hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")  // 鉴权管理器配置
//                .anyExchange().authenticated()  // 鉴权管理器配置
                .and().exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)  // 处理未授权
                .authenticationEntryPoint(restfulAuthenticafultionEntryPoint)  // 处理未认证
                .and()
                .csrf().disable()
        ;
        return http.build();

    }

}
