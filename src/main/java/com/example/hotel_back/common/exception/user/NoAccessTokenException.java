package com.example.hotel_back.common.exception.user;

import lombok.Getter;

@Getter
public class NoAccessTokenException extends RuntimeException {

	private String message;

	public NoAccessTokenException(String message) {

		super(message);
		this.message = message;

	}
}
