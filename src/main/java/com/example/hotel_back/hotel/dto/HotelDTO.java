package com.example.hotel_back.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {

	private Long hotelId;
	private String location;
	private String name;
	private String tel;
	private String owner;
	private String imagePath;

}
