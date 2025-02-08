package com.example.springbootstart.exception;

public class DuplicateRequestException extends RuntimeException {

    public DuplicateRequestException() {
    }

    public DuplicateRequestException(String message) {
        super(message);
    }

    public DuplicateRequestException(Throwable cause) {
        super(cause);
    }
}
