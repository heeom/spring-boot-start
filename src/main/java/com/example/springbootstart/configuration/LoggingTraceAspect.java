package com.example.springbootstart.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



@Aspect
@Component
public class LoggingTraceAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingTraceAspect.class);

    @Around("@annotation(com.example.springbootstart.annotation.Trace)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getHeaderNames().asIterator().forEachRemaining(header -> {
            String value = request.getHeader(header);
            logger.info("Request Header - {} : {}", header, value);
        });
        return joinPoint.proceed();
    }
}
