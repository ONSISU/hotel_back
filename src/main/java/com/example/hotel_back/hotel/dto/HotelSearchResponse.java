package com.example.hotel_back.hotel.dto;

import com.example.hotel_back.ownhotel.dto.OwnHotelDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelSearchResponse {

	private Long hotelId;
	private String hotelType;
	private Double price;
	private String location;
	private List<OwnHotelDTO> ownHotelList;

}
