package com.example.springbootstart.exception;

import com.example.springbootstart.annotation.SpecialController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = SpecialController.class)
public class CommonExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        logger.info("IllegalArgumentException Handler : {}", e.getMessage());
        return ResponseEntity.badRequest().body("Handled By illegalArgumentExceptionHandler");
    }
}
