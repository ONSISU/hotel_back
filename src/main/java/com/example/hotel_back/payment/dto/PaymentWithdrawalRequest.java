package com.example.hotel_back.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "TOSS 결제승인을 위한 요청 DTO")
public class PaymentWithdrawalRequest {

	@Schema(description = "FE의 TOSS결제 후 받은 paymentKey")
	private String paymentKey;

	@Schema(description = "FE의 TOSS결제 후 받은 orderId")
	private String orderId;

	@Schema(description = "saveTempReservation에서 받은 orderKey")
	private String orderKey;
}
