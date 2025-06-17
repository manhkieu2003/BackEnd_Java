package com.example.backend_java.exception;

public class ResourceNotfoundException extends RuntimeException {
    public ResourceNotfoundException(String message) {
        super(message);
    }
}
