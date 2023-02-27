package com.example.gatewaty.config;

import cn.hutool.core.util.ArrayUtil;
import com.example.gatewaty.component.RestfulAccessDeniedHandler;
import com.example.gatewaty.component.RestfulAuthenticationEntryPoint;
import com.example.gatewaty.constant.AuthConstant;
import com.example.gatewaty.filter.IgnoreUrlsRemoveJwtFilter;
import com.example.gatewaty.properties.IgnoreUrlsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;


/**
 * @author wzklhk
 */
@EnableWebFluxSecurity
@Configuration
public class ResourceServerConfig {

    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    private final RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint;

    private final IgnoreUrlsProperties ignoreUrlsProperties;

    private final IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

    public ResourceServerConfig(RestfulAccessDeniedHandler restfulAccessDeniedHandler, RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint, IgnoreUrlsProperties ignoreUrlsProperties, IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter) {
        this.restfulAccessDeniedHandler = restfulAccessDeniedHandler;
        this.restfulAuthenticationEntryPoint = restfulAuthenticationEntryPoint;
        this.ignoreUrlsProperties = ignoreUrlsProperties;
        this.ignoreUrlsRemoveJwtFilter = ignoreUrlsRemoveJwtFilter;
    }

    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity http) {
        //
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
        // 自定义处理JWT请求头过期或签名错误的结果
        http.oauth2ResourceServer().authenticationEntryPoint(restfulAuthenticationEntryPoint);
        // 对白名单路径，直接移除JWT请求头
        http.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        http.authorizeExchange()
                // 白名单配置
                .pathMatchers(ArrayUtil.toArray(ignoreUrlsProperties.getUrls(), String.class)).permitAll()
                .anyExchange().authenticated()
                .and().exceptionHandling()
                //处理未授权
                .accessDeniedHandler(restfulAccessDeniedHandler)
                //处理未认证
                .authenticationEntryPoint(restfulAuthenticationEntryPoint)
                .and().csrf().disable()
        ;
        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
