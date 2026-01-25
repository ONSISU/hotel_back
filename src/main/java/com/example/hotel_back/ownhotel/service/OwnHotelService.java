package com.example.hotel_back.ownhotel.service;

import com.example.hotel_back.ownhotel.mapper.OwnHotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnHotelService {

	private final OwnHotelMapper ownHotelMapper;

	public int getAvailableNumber(Long ownHotelId) {
		return ownHotelMapper.getAvailableNumber(ownHotelId);
	}
}
