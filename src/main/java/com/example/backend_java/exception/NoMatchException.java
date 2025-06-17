package com.example.backend_java.exception;

public class NoMatchException extends RuntimeException {
    public NoMatchException(String message) {
        super(message);
    }
}
