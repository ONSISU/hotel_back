package com.example.hotel_back.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(description = "결제 숙소예약 가능여부 요청 DTO")
public class PaymentAvailableHotelRequest {

	@Schema(description = "소유호텔PK", example = "3")
	private String ownHotelId;

	@Schema(description = "예약시작일", example = "2025_11_06")
	private String startDate;

	@Schema(description = "예약종료일", example = "2025_11_09")
	private String endDate;
}
