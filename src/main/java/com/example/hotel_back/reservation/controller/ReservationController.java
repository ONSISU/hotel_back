package com.example.hotel_back.reservation.controller;

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

	@Schema()
	@PostMapping("/room")
	public ReservedRoom reserveRoom(@RequestBody ReserveRoomRequest request) {
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
