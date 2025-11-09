package com.example.hotel_back.common.exception.hotel;

import lombok.Getter;

public class NoWithinMarkerRange extends RuntimeException{

    public NoWithinMarkerRange(String message) {
        super(message);
    }

}
