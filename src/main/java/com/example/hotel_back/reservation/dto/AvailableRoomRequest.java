package com.example.hotel_back.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

	@Schema(description = "호텔PK", example = "1")
	private Long hotelId;

	@Schema(description = "예약시작일", example = "2025_11_06")
	private LocalDate startDate;

	@Schema(description = "예약종료일", example = "2025_11_06")
	private LocalDate endDate;

	@Schema(description = "파라미터에는 사용x", example = "2025_11_06")
	private List<Long> ownHotelIdList;

}
