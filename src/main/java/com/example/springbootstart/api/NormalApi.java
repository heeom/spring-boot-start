package com.example.springbootstart.api;

import com.example.springbootstart.application.ExceptionInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/normal")
public class NormalApi {

    private static final Logger logger = LoggerFactory.getLogger(NormalApi.class);

    private final ExceptionInvoker exceptionInvoker;

    public NormalApi(ExceptionInvoker exceptionInvoker) {
        this.exceptionInvoker = exceptionInvoker;
    }

    @GetMapping
    public String index() {
        logger.info("NormalApi index");
        exceptionInvoker.throwIllegalArgumentException();
        return "ok";
    }
}
