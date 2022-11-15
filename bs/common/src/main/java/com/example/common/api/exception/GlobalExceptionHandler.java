package com.example.common.api.exception;

import com.example.common.api.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice  // aop添加异常处理
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultInfo handlerException(Exception e) {
        log.error("未知异常: {}", e.getMessage());
        return ResultInfo.error(e.getMessage());
    }

}
