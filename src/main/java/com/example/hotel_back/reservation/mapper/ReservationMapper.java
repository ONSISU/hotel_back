package com.example.hotel_back.reservation.mapper;

import com.example.hotel_back.ownhotel.dto.OwnHotelDTO;
import com.example.hotel_back.reservation.dto.AvailableRoomRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationMapper {
	List<OwnHotelDTO> getAvailableRooms(AvailableRoomRequest request);
}
