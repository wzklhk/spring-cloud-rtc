package com.example.access.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "auth.oauth2")
public class OAuth2Properties {

    private String clientId;

    private String clientSecret;

    private String[] scopes;

    private String[] authorizedGrantTypes;

    private int accessTokenValiditySeconds;

    private int refreshTokenValiditySeconds;
}
