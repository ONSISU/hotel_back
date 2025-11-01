package com.example.hotel_back.common.exception.user;

import lombok.Getter;

@Getter
public class DuplicationUserException extends RuntimeException{

    private String message;

    public DuplicationUserException(String message) {
        super(message);

        this.message = message;
    }

}
