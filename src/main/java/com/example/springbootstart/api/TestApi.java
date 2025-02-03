package com.example.springbootstart.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/idempotency")
public class TestApi {

    private static final Logger logger = LoggerFactory.getLogger(TestApi.class);

    @PostMapping
    public void post(@RequestBody String request) {
        logger.info("post. request: {}", request);
    }

    @PutMapping
    public void put(@RequestBody String request) {
        logger.info("put. request: {}", request);
    }

    @GetMapping
    public void get(@RequestBody String request) {
        logger.info("get. request: {}", request);
    }
}
