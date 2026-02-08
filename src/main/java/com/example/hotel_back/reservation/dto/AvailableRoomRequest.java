package com.example.hotel_back.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailableRoomRequest {
	private Long hotelId;
	private LocalDate startDate;
	private LocalDate endDate;
	private List<Long> ownHotelIdList;
}
