package com.example.hotel_back.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SaveTempReservationRequest {

	@Schema(description = "소유호텔PK", example = "3")
	private String ownHotelId;


}
