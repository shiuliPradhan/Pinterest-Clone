package com.infy.pinterest.utility;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.infy.pinterest.service..*(..))")
    public void beforeService(JoinPoint jp) {
        log.info("Executing: {} with args {}", jp.getSignature(), jp.getArgs());
    }

    @AfterReturning(
            value = "execution(* com.infy.pinterest.service..*(..))",
            returning = "result"
    )
    public void afterService(JoinPoint jp, Object result) {
        log.info("Completed: {} with result {}", jp.getSignature(), result);
    }

    @AfterThrowing(
            value = "execution(* com.infy.pinterest.service..*(..))",
            throwing = "ex"
    )
    public void afterException(JoinPoint jp, Exception ex) {
        log.error("Error in {}: {}", jp.getSignature(), ex.getMessage());
    }
}

