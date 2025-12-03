package com.example.hotel_back.common.exception.reservation;

public class isNotValidTokenException extends RuntimeException {
	public isNotValidTokenException(String message) {
		super(message);
	}
}
