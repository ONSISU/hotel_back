package com.example.hotel_back.hotel.dto;

public interface HotelDetailProjection {


	Long getHotelId();

	String getPictureUrl();

	String getHotelName();

	String getLocation();

	String getBusinessNumber();

	String getRegistNumber();

	String getTel();

	String getDescription();

	Double getLatitude();

	Double getLongitude();

	Integer getRoomCounts();
}
