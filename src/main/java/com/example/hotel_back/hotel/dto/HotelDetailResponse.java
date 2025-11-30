package com.example.hotel_back.hotel.dto;

import com.example.hotel_back.ownhotel.dto.OwnHotelDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelDetailResponse {

	private Long hotelId;

	private String pictureUrl;

	private String location;

	private Double latitude;
	private Double longitude;

	private List<OwnHotelDTO> ownHotelList;

	private Integer roomCounts;

}
