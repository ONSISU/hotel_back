package com.example.hotel_back.reservation.controller;

import com.example.hotel_back.common.exception.hotel.NotFoundHotel;
import com.example.hotel_back.common.util.JwtUtil;
import com.example.hotel_back.hotel.service.HotelService;
import com.example.hotel_back.ownhotel.dto.OwnHotelDTO;
import com.example.hotel_back.reservation.dto.AvailableRoomRequest;
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
	private final HotelService hotelService;
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

	@PostMapping("/getAvailableRooms")
	public List<OwnHotelDTO> getAvailableRooms(@RequestBody AvailableRoomRequest request) {
		Long hotelId = request.getHotelId();

		if (hotelId == null) {
			throw new NotFoundHotel("호텔 ID는 필수 입니다");
		}

		List<Long> ownHotelIdList = hotelService.getOwnHotelIdList(hotelId);
		request.setOwnHotelIdList(ownHotelIdList);

		List<OwnHotelDTO> list = reservationService.getAvailableRooms(request);

		return list;
	}
}
