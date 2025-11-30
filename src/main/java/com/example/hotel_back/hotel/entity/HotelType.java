package com.example.hotel_back.hotel.entity;

import org.springframework.security.core.parameters.P;

public enum HotelType {
	HOTEL("호텔"),
	VILLA("빌라"),
	APT("아파트");

	private final String type;

	HotelType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
