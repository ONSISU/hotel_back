package com.example.hotel_back.payment.controller;

import com.example.hotel_back.common.exception.hotel.NoWithinMarkerRange;
import com.example.hotel_back.common.exception.reservation.OverBookReservation;
import com.example.hotel_back.ownhotel.service.OwnHotelService;
import com.example.hotel_back.payment.dto.PaymentAvailableHotelRequest;
import com.example.hotel_back.payment.dto.PaymentTemp;
import com.example.hotel_back.payment.dto.SaveTempReservationRequest;
import com.example.hotel_back.payment.service.PaymentRedisService;
import com.example.hotel_back.payment.service.PaymentService;
import com.example.hotel_back.reservation.dto.ReserveRoomRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// ✅필) 결제흐름 문서 확인
@Tag(name = "결제", description = "결제 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

	private final PaymentService paymentService;
	private final OwnHotelService ownHotelService;
	private final PaymentRedisService paymentRedisService;

	@GetMapping("/test")
	public int test() {
		return paymentService.test();
	}

	@Operation(
					summary = "[호텔결제] 1.호텔예약가능여부",
					description = "호텔예약가능여부 요청한다"
	)
	@PostMapping("/checkReserveYn")
	public Map<String, Boolean> checkReserveYn(@RequestBody PaymentAvailableHotelRequest request) {
		// 해당일자 예약 개수
		Integer numberOfreserved = paymentService.checkAvailablePayHotel(request);
		Integer numberOfAvailable = ownHotelService.getAvailableNumber(Long.valueOf(request.getOwnHotelId()));

		if (numberOfreserved >= numberOfAvailable) {
			throw new OverBookReservation("예약초과");
		}

		Map<String, Boolean> result = new HashMap<>();
		result.put("isAvailable", true);

		return result;
	}

	@Operation(
					summary = "[호텔결제]2.결제 데이터 임시 저장",
					description = "호텔 결제 정합성을 위해 임시데이터 저장"
	)
	@PostMapping("/saveTempReservation")
	public Map<String, Boolean> checkReserveYn(@RequestBody PaymentTemp request) {

		// redis 저장
		request.setPaymentTempId(UUID.randomUUID().toString());
		paymentRedisService.save(request);

		Map<String, Boolean> result = new HashMap<>();
		result.put("isAvailable", true);

		return result;
	}

}
