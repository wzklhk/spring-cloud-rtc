package com.example.common.exception;

import com.example.common.pojo.CommonResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 注解@RestControllerAdvice // aop添加异常处理
 *
 * @author wzklhk
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public CommonResultInfo<Object> handlerException(Exception e) {
        log.error("未知异常: {}", e.getMessage());
        return CommonResultInfo.error("未知异常: " + e.getMessage());
    }

}
