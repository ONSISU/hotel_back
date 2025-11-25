package com.example.hotel_back.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservedRoom {

	private Long roomId;
	private Long hotelId;
	private String hotelName;
	private String location;
	private Double latitude;
	private Double longitude;

}
