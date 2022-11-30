package com.example.access.auth.pojo.token;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Oauth2获取Token返回信息封装
 *
 * @author wzklhk
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class OAuth2AccessTokenDTO {

    /**
     * 访问令牌头前缀
     */
    private String tokenType;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    private Boolean isExpired;

    /**
     * 有效时间（秒）
     */
    private Integer expiresIn;

    private Date expiration;

    /**
     * scope
     */
    private Set<String> scope;

    /**
     * 用户信息
     */
    private Map<String, Object> additionalInformation;
}
