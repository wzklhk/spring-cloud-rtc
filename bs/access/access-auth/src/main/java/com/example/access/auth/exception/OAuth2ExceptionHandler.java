package com.example.access.auth.exception;

import com.example.common.api.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wzklhk
 */
@Slf4j
@RestControllerAdvice
public class OAuth2ExceptionHandler {
    @ExceptionHandler(OAuth2Exception.class)
    public ResultInfo handleOAuth2Exception(OAuth2Exception e) {
        log.error("OAuth2Exception: {}", e.getMessage());
        return ResultInfo.error(e.getMessage());
    }
}
