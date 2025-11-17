package com.rantomah.boilerplate.core.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingConfig {

    Logger accessLogger = LoggerFactory.getLogger("x-access");
    Logger errorLogger = LoggerFactory.getLogger("x-exception");

    @Before(
            "(execution(public * *(..))) && ("
                    + "@annotation(org.springframework.web.bind.annotation.RequestMapping) || "
                    + "@annotation(org.springframework.web.bind.annotation.GetMapping) || "
                    + "@annotation(org.springframework.web.bind.annotation.PostMapping) || "
                    + "@annotation(org.springframework.web.bind.annotation.PutMapping) || "
                    + "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || "
                    + "@annotation(org.springframework.web.bind.annotation.PatchMapping))")
    public void logAccess(JoinPoint joinPoint) {
        accessLogger.info("Entering method: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(
            pointcut =
                    "execution(public * *(..)) && @within(org.springframework.stereotype.Service)",
            throwing = "ex")
    public void logError(JoinPoint joinPoint, Throwable ex) {
        errorLogger.error("Exception in method: {}", joinPoint.getSignature().getName(), ex);
    }
}
