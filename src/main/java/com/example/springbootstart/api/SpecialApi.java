package com.example.springbootstart.api;

import com.example.springbootstart.annotation.SpecialController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpecialController
@RequestMapping("/api/special")
public class SpecialApi {

    private static final Logger logger = LoggerFactory.getLogger(SpecialApi.class);

    @GetMapping
    public String index() {
        logger.info("SpecialApi index");
        return "ok";
    }
}
