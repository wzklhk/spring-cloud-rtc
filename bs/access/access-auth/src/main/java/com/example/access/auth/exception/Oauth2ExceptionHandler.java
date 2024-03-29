package com.example.access.auth.exception;

import com.example.common.pojo.CommonResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wzklhk
 */
@Slf4j
@RestControllerAdvice
public class Oauth2ExceptionHandler {
    @ExceptionHandler(OAuth2Exception.class)
    public CommonResultInfo handleOAuth2Exception(OAuth2Exception e) {
        log.error("OAuth2Exception: {}", e.getMessage());
        return CommonResultInfo.error(e.getMessage());
    }
}
