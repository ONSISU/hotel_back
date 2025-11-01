package com.example.hotel_back.common.exception.user;

import lombok.Getter;

@Getter
public class NoSuchMemberException extends RuntimeException{
    private String message;

    public NoSuchMemberException(String message) {
        super(message);

        this.message = message;
    }
}
