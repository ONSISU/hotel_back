package com.example.hotel_back.payment.controller;

import com.example.hotel_back.common.exception.reservation.OverBookReservation;
import com.example.hotel_back.common.exception.user.NoAccessTokenException;
import com.example.hotel_back.common.util.JwtUtil;
import com.example.hotel_back.ownhotel.service.OwnHotelService;
import com.example.hotel_back.payment.dto.*;
import com.example.hotel_back.payment.entity.Payment;
import com.example.hotel_back.payment.service.PaymentRedisService;
import com.example.hotel_back.payment.service.PaymentService;
import com.example.hotel_back.reservation.dto.ReserveRoomRequest;
import com.example.hotel_back.reservation.dto.ReservedRoom;
import com.example.hotel_back.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

// ✅필) 결제흐름 문서 확인
@Tag(name = "결제", description = "결제 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

	private final PaymentService paymentService;
	private final OwnHotelService ownHotelService;
	private final ReservationService reservationService;
	private final PaymentRedisService paymentRedisService;
	private final JwtUtil jwtUtil;

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
	public Map<String, Object> checkReserveYn(@RequestBody PaymentTemp request) {

		String orderKey = UUID.randomUUID().toString();

		// redis 저장
		request.setPaymentTempId(orderKey);
		paymentRedisService.save(request);

		Map<String, Object> result = new HashMap<>();
		result.put("isAvailable", true);
		result.put("orderKey", orderKey);

		return result;
	}


	@Operation(
					summary = "[호텔결제]3.결제승인처리",
					description = "호텔 결제 정합성을 위해 임시데이터 저장"
	)
	@PostMapping("/saveReservation")
	public Map<String, Object> saveReservation(@RequestBody PaymentWithdrawalRequest request) throws BadCredentialsException {

		// redis 조회
		PaymentTemp temp = paymentRedisService.get(request.getOrderKey());

		if (temp == null) {
			throw new RuntimeException("임시저장 데이터가 없습니다.");
		}

		// TOSS PAY 결제승인 API 추가
		String url = "https://api.tosspayments.com/v1/payments/confirm";
		String secretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";

		// 1. Authorization Header (Basic Auth)
		String auth = Base64.getEncoder()
						.encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic " + auth);

		// 2. Request Body
		Map<String, Object> body = new HashMap<>();
		body.put("paymentKey", request.getPaymentKey());
		body.put("orderId", request.getOrderId());
		body.put("amount", temp.getAmt());

		HttpEntity<Map<String, Object>> tossRequest =
						new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();

		// 3. API Call
		ResponseEntity<TossConfirmResponse> response =
						restTemplate.postForEntity(
										url,
										tossRequest,
										TossConfirmResponse.class
						);

		TossConfirmResponse res = response.getBody();

		ReserveRoomRequest reserveRoomRequest = ReserveRoomRequest.builder()
						.userId(temp.getUserId())
						.ownHotelId(temp.getOwnHotelId())
						.startDate(temp.getStartDate())
						.endDate(temp.getEndDate())
						.reserveName(temp.getReserveName())
						.reservePhone(temp.getReservePhone())
						.build();


		ReservedRoom reservedRoom = reservationService.reserveHotel(reserveRoomRequest);


		Map<String, Object> result = new HashMap<>();
		result.put("isCompletedRservation", true);
		result.put("reservedRoom", reservedRoom);


		// payment, reservation 추가하기


		return result;
	}

	// 멤버만 볼 수 있도록 검열 확인 필요
	@GetMapping("/getPaymentDetail/{id}")
	public Payment getPaymentDetail(@PathVariable Long id) {
		return paymentService.getPaymentDetail(id);
	}

	@GetMapping("/payments")
	public List<Payment> getPayments(
					@CookieValue(name = "accessToken", required = false) String accessToken,
					@CookieValue(name = "refreshToken", required = false) String refreshToken,
					@RequestHeader("Authorization") String authorization
	) {

		if (accessToken == null && refreshToken == null && authorization == null) {
			throw new NoAccessTokenException("토큰이 없습니다.");
		}

		String token = accessToken != null
						? accessToken
						: refreshToken != null
						? refreshToken
						: authorization;

		String email = (String) jwtUtil.getClaims(token).get("sub");

		return paymentService.getPaymentList(email);

	}

}
