package com.example.access.auth.controller;

import com.example.access.auth.pojo.token.OAuth2AccessTokenDTO;
import com.example.common.api.ResultInfo;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * 自定义Oauth2获取令牌接口
 * <p>
 * Oauth2登录认证
 *
 * @author wzklhk
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {

    private final TokenEndpoint tokenEndpoint;

    public AuthController(TokenEndpoint tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    /**
     * Oauth2登录认证
     */
    @PostMapping("/token")
    public ResultInfo<OAuth2AccessTokenDTO> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        OAuth2AccessTokenDTO token = OAuth2AccessTokenDTO.builder()
                .tokenType(oAuth2AccessToken.getTokenType())
                .accessToken(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .isExpired(oAuth2AccessToken.isExpired())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .expiration(oAuth2AccessToken.getExpiration())
                .scope(oAuth2AccessToken.getScope())
                .additionalInformation(oAuth2AccessToken.getAdditionalInformation())
                .build();
        return ResultInfo.ok(token);
    }
}
