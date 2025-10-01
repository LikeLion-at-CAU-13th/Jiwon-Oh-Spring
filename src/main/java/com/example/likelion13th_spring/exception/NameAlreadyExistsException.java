package com.example.likelion13th_spring.exception;

public class NameAlreadyExistsException extends RuntimeException {
    private final String requestedValue;

    public NameAlreadyExistsException(String name) {
        super("이미 존재하는 name 입니다. name: " + name);
        this.requestedValue = name;
    }

    public String getRequestedValue() {
        return requestedValue;
    }
}