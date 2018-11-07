package com.example.webmvc.service;

public class UnknownNewsUserException extends RuntimeException {
    public UnknownNewsUserException(String message) {
        super(message);
    }
}
