package com.example.backend.common.exception;

public class InactiveUserException extends RuntimeException {

    public InactiveUserException(Long userId) {
        super("User account is deactivated: " + userId);
    }

    public InactiveUserException(String message) {
        super(message);
    }
}
