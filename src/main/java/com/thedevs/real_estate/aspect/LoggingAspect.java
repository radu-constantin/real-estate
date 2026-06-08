package com.thedevs.real_estate.aspect; // ⚠️ change to your package

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.thedevs.real_estate.service.*.*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        String className  = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args     = joinPoint.getArgs();

        log.debug("→ {}.{}() | args: {}", className, methodName, Arrays.toString(args));

        long start = System.currentTimeMillis();

        try {
            Object result    = joinPoint.proceed();
            long   duration  = System.currentTimeMillis() - start;

            log.debug("← {}.{}() | completed in {}ms", className, methodName, duration);
            return result;

        } catch (Exception e) {
            log.error("✗ {}.{}() | threw {}: {}",
                    className, methodName,
                    e.getClass().getSimpleName(), e.getMessage(), e);
            throw e;
        }
    }
}