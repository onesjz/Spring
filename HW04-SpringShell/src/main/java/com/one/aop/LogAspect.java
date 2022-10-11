package com.one.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Before("@annotation(com.one.aop.Logging)")
    private void logBefore(JoinPoint joinPoint) {
        log.debug("Method {} was called with args: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }
}
