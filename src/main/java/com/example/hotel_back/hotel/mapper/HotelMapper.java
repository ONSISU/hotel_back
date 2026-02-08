package com.example.hotel_back.hotel.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HotelMapper {
	List<Long> getOwnHotelIdList(@Param("hotelId") Long hotelId);
}
