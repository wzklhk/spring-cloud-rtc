package com.example.access.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wzklhk
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth.jwt")
public class JWTProperties {

    private String keyPath;

    private String keyAlias;

    private String keyPassword;
}
