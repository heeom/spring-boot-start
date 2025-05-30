package com.example.springbootstart.api;

import com.example.springbootstart.annotation.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/idempotency")
public class TestApi {

    private static final Logger logger = LoggerFactory.getLogger(TestApi.class);

    @Trace
    @PostMapping
    public void post(@RequestBody String request) {
        logger.info("post. request: {}", request);
    }

    @PutMapping
    public void put(@RequestBody String request) {
        logger.info("put. request: {}", request);
    }

    @GetMapping
    public void get() {
        logger.info("get");
    }

    @DeleteMapping
    public void delete() {
        logger.info("delete");
    }
}
