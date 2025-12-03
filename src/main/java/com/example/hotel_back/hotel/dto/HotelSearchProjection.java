package com.example.hotel_back.hotel.dto;

public interface HotelSearchProjection {

	Long getHotelId();

	String getHotelType();

	String getHotelName();

	String getRegistNumber();

	String getBusinessNumber();

	Double getMinPrice();

	Double getMaxPrice();

	String getLocation();
}
