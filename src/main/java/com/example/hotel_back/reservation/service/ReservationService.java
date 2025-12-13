package com.example.hotel_back.reservation.service;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.ownhotel.entity.OwnHotel;
import com.example.hotel_back.ownhotel.repository.OwnHotelRepository;
import com.example.hotel_back.reservation.dto.ReserveRoomRequest;
import com.example.hotel_back.reservation.dto.ReservedRoom;
import com.example.hotel_back.reservation.entity.Reservation;
import com.example.hotel_back.reservation.repository.ReservationRepository;
import com.example.hotel_back.user.entity.User;
import com.example.hotel_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

		private final ReservationRepository reservationRepository;
		private final HotelRepository hotelRepository;
		private final UserRepository userRepository;
		private final OwnHotelRepository ownHotelRepository;

		// 유저가 A호텔의 A객실을 예약한다.
		public ReservedRoom reserveHotel(ReserveRoomRequest request) {

				Long hotelId = request.getOwnHotelId();
				Long ownHotelId = request.getOwnHotelId();
				Long userId = request.getUserId();
				String reservedName = request.getReserveName();
				String reservedPhone = request.getReservePhone();

				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String email = authentication.getName();

				// 해당 일자에 이미 예약되었는지 확인

				Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
				OwnHotel ownHotel = ownHotelRepository.findByOwnHotelId(ownHotelId);
				User user = userRepository.findById(userId).orElseThrow();


				// ✅ userId와 토큰 id와 일치하는지 확인
				if (!user.getEmail().equals(email)) {
						throw new BadCredentialsException("올바르지 않은 요청입니다. 로그인 정보와 일치하지 않는 UserID");
				}

				LocalDate startDate = request.getStartDate();
				LocalDate endDate = request.getEndDate();

				// 예약하기
				Reservation reservation = Reservation.builder()
												.startDate(startDate)
												.endDate(endDate)
												.hotel(hotel)
												.ownHotel(ownHotel)
												.user(user)
												.reservedName(reservedName)
												.reservedPhone(reservedPhone)
												.build();

				reservationRepository.save(reservation);

				ReservedRoom reservedRoom = ReservedRoom.builder()
												.roomId(ownHotelId)
												.hotelId(hotelId)
												.location(hotel.getLocation())
												.hotelName(hotel.getName())
												.latitude(hotel.getLatitude())
												.longitude(hotel.getLongitude())
												.reserveName(reservedName)
												.reservePhone(reservedPhone)
												.build();

				return reservedRoom;
		}

		// 유저의 예약한 객실 리스트
		public List<ReservedRoom> getReservedRooms(String email) {
				User user = userRepository.findByEmail(email).orElseThrow();

				List<Reservation> list2 = reservationRepository
												.findAllByUserId(user.getUserId());

				List<ReservedRoom> list = reservationRepository
												.findAllByUserId(user.getUserId())
												.stream()
												.map(r ->
																				ReservedRoom.builder()
																												.roomId(r.getOwnHotel().getOwnHotelId())
																												.hotelId(r.getHotel().getHotelId())
																												.hotelName(r.getHotel().getName())
																												.location(r.getHotel().getLocation())
																												.latitude(r.getHotel().getLatitude())
																												.longitude(r.getHotel().getLongitude())
																												.reservePhone(r.getReservedPhone())
																												.reserveName(r.getReservedName())
																												.build()
												).toList();
				return list;
		}
}
