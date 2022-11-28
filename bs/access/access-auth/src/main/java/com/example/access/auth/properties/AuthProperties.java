package com.example.access.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * 认证属性配置
 *
 * @author wzklhk
 */

@Data
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    @NestedConfigurationProperty
    private OAuth2Properties oAuth2;

    @NestedConfigurationProperty
    private JWTProperties jwt;
}
