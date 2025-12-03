package com.example.hotel_back.reservation.controller;

import com.example.hotel_back.common.exception.reservation.isNotValidTokenException;
import com.example.hotel_back.common.util.JwtUtil;
import com.example.hotel_back.reservation.dto.ReserveRoomRequest;
import com.example.hotel_back.reservation.dto.ReservedRoom;
import com.example.hotel_back.reservation.service.ReservationService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {

	private final ReservationService reservationService;
	private final JwtUtil jwtUtil;

	@PostMapping("/room")
	public ReservedRoom reserveRoom(@RequestHeader("Authorization") String token, @RequestBody ReserveRoomRequest request) {
		System.out.println("token >>> " + token);

		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		Boolean isValid = jwtUtil.validateToken(token);

		if (!isValid) {
			throw new isNotValidTokenException("유효한 토큰이 아닙니다.");
		}

		return reservationService.reserveHotel(request);
	}

	@GetMapping("/reservations")
	public List<ReservedRoom> getReservedRooms(@RequestHeader("Authorization") String token) {
		String accessToken = token.replace("Bearer ", "");
		Claims claims = jwtUtil.getClaims(accessToken);
		String email = (String) claims.get("sub");

		return reservationService.getReservedRooms(email);
	}
}
