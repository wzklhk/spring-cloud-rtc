package com.example.service.rtc.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author wzklhk
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.example.service.rtc.api.controller.WebsocketController.*(..))")
    public void wsControllerPointcut() {
    }

    @Before(value = "wsControllerPointcut()")
    public void beforeWsPointcut() {
        log.info("test Before aspect");
    }

    @After(value = "wsControllerPointcut()")
    public void afterWsPointcut() {
        log.info("test After aspect");
    }

    @AfterReturning(value = "wsControllerPointcut()")
    public void afterReturningWsPointcut() {
        log.info("test AfterReturning aspect");
    }
}
