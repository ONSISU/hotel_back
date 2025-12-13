package com.example.hotel_back.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReserveRoomRequest {

		// 호텔 예약시 요청
		private Long userId;
		private Long ownHotelId;
		private LocalDate startDate;
		private LocalDate endDate;
		private String reserveName;
		private String reservePhone;

}
