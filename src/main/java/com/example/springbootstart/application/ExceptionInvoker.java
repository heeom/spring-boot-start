package com.example.springbootstart.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExceptionInvoker {

    private final Logger logger = LoggerFactory.getLogger(ExceptionInvoker.class);

    public void throwIllegalArgumentException() {
        logger.error("ExceptionInvoker throw ===> IllegalArgumentException");
        throw new IllegalArgumentException("ExceptionInvoker throw ===> IllegalArgumentException");
    }
}
