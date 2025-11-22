package com.example.hotel_back.common.exception.reservation;

public class OverReservationException extends RuntimeException{
    public OverReservationException(String message) {
        super(message);
    }
}
