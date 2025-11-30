package com.example.hotel_back.hotel.dto;

public interface HotelDetailProjection {

	Long getHotelId();

	String getPictureUrl();

	String getHotelName();

	String getLocation();

	String getDescription();

	Double getLatitude();

	Double getLongitude();

	Integer getRoomCounts();
}
