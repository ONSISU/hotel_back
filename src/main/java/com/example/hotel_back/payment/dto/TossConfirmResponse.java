package com.example.hotel_back.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Toss 결제 승인 응답 DTO")
public class TossConfirmResponse {

	@Schema(description = "결제 키", example = "pay_abc123")
	private String paymentKey;

	@Schema(description = "주문 ID", example = "ORD-20260101")
	private String orderId;

	@Schema(description = "결제 상태", example = "DONE")
	private String status;

	@Schema(description = "결제 금액", example = "15000")
	private Long totalAmount;

	@Schema(description = "결제 승인 시각", example = "2026-01-01T12:30:45+09:00")
	private String approvedAt;

	@Schema(description = "결제 수단", example = "카드")
	private String method;

	@Schema(description = "결제 주문명", example = "호텔 예약 결제")
	private String orderName;

	@Schema(description = "결제 통화", example = "KRW")
	private String currency;
}

