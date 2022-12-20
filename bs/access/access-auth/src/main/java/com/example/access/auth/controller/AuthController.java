package com.example.access.auth.controller;

import com.example.access.auth.pojo.token.OAuth2AccessTokenDTO;
import com.example.common.pojo.CommonResultInfo;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final CheckTokenEndpoint checkTokenEndpoint;

    public AuthController(TokenEndpoint tokenEndpoint, CheckTokenEndpoint checkTokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
        this.checkTokenEndpoint = checkTokenEndpoint;
    }

    /**
     * Oauth2登录认证
     */
    @GetMapping("/token")
    public CommonResultInfo<Object> getAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        try {
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
            return CommonResultInfo.ok(token);
        } catch (Exception e) {
            return CommonResultInfo.error(e.getMessage());
        }
    }

    @GetMapping("/check_token")
    public CommonResultInfo<Object> checkToken(@RequestParam String token) {
        try {
            Map<String, ?> map = checkTokenEndpoint.checkToken(token);
            return CommonResultInfo.ok(map);
        } catch (Exception e) {
            return CommonResultInfo.error(e.getMessage());
        }
    }
}
