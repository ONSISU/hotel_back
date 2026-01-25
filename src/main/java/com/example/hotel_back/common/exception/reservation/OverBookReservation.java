package com.example.hotel_back.common.exception.reservation;

public class OverBookReservation extends RuntimeException {
	public OverBookReservation(String message) {
		super(message);
	}
}
