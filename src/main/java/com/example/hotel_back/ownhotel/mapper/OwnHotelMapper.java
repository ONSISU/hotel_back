package com.example.hotel_back.ownhotel.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OwnHotelMapper {
	int getAvailableNumber(Long ownHotelId);
}
