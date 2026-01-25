package com.example.hotel_back.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentTemp {

	// 호텔 예약시 요청
	@Schema(description = "예약데이터임시PK(필수아님)", example = "UUID로생성")
	private String paymentTempId;

	@NotBlank
	@Schema(description = "예약할고객PK", example = "2")
	private Long userId;

	@NotBlank
	@Schema(description = "예약할소유숙소PK", example = "65")
	private Long ownHotelId;

	@NotBlank
	@Schema(description = "숙소예약체크인", example = "2025-01-01")
	private LocalDate startDate;

	@NotBlank
	@Schema(description = "숙소예약체크아웃", example = "2025-01-10")
	private LocalDate endDate;

	@NotBlank
	@Schema(description = "예약자명", example = "남대리")
	private String reserveName;

	@NotBlank
	@Schema(description = "예약자휴대폰번호", example = "010-2020-3030")
	private String reservePhone;

	@Schema(description = "결제상태(필수아님)", example = "PENDING | PAID")
	private String status;

	@NotBlank
	@Schema(description = "금액", example = "120000")
	private String amt;

	@NotBlank
	@Schema(description = "필수아님", example = "2025-01-20")
	private String expireAt;

}
