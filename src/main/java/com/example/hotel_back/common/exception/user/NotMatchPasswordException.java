package com.example.hotel_back.common.exception.user;

public class NotMatchPasswordException extends RuntimeException{
    private String message;

    public NotMatchPasswordException(String message) {
        super(message);

        this.message = message;
    }
}
