package com.example.access.auth.pojo.token;

import com.example.access.auth.pojo.user.UserDetailsImpl;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Oauth2获取Token返回信息封装
 *
 * @author wzklhk
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class OAuth2TokenDTO {

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

    /**
     * 有效时间（秒）
     */
    private Integer expiresIn;

    /**
     * scope
     */
    private String[] scope;

    /**
     * 用户信息
     */
    private UserDetailsImpl user;
}
