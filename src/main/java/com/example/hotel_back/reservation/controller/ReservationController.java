package com.example.hotel_back.reservation.controller;

import com.example.hotel_back.common.util.JwtUtil;
import com.example.hotel_back.reservation.dto.ReserveRoomRequest;
import com.example.hotel_back.reservation.dto.ReservedRoom;
import com.example.hotel_back.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/reservation")
public class ReservationController {

		private final ReservationService reservationService;
		private final JwtUtil jwtUtil;

		@PostMapping("/room")
		public ReservedRoom reserveRoom(@RequestBody ReserveRoomRequest request) {
				return reservationService.reserveHotel(request);
		}

		@GetMapping("/reservations")
		public List<ReservedRoom> getReservedRooms(@AuthenticationPrincipal UserDetails userDetails) {
				String email = userDetails.getUsername();
				return reservationService.getReservedRooms(email);
		}
}
