package com.example.hotel_back.reservation.service;

import com.example.hotel_back.common.exception.reservation.OverReservationException;
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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

				// 로그인한 토큰으로부터의 이메일 가져오기
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String email = authentication.getName();

				Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
				OwnHotel ownHotel = ownHotelRepository.findByOwnHotelId(ownHotelId);
				User user = userRepository.findById(userId).orElseThrow();


				// ✅ userId와 토큰 id와 일치하는지 확인
				if (!user.getEmail().equals(email)) {
						throw new BadCredentialsException("올바르지 않은 요청입니다. 로그인 정보와 일치하지 않는 UserID");
				}

				LocalDate startDate = request.getStartDate();
				LocalDate endDate = request.getEndDate();


				// ✅ 해당 일자에 이미 예약되었는지 확인
				List<Reservation> reservedList = reservationRepository.findAllByOwnHotelAndDate(ownHotel, startDate, endDate);
				Integer 방개수 = ownHotel.getCountRoom();


				/** [예약초과확인]
					* 12.12 - 12.13 예약시 중간에 걸려있는 예약 정보 확인
					* ex) 12.10 - 12.14 이런 케이스의 예약자들도 확인해야함
					*/

				if (방개수 <= reservedList.size()) {
						List<LocalDate> fullDateList = new ArrayList<>();

						for (Reservation r : reservedList) {
								int nights = (int) ChronoUnit.DAYS.between(r.getStartDate(), r.getEndDate());
								List<LocalDate> reservedDateList = IntStream.range(0, nights)
																.mapToObj(r.getStartDate()::plusDays)
																.toList();

								fullDateList.addAll(reservedDateList);
						}

						// 날짜에 그룹핑하여 예약일자 체크
						Map<LocalDate, Long> countMap =
														fullDateList.stream()
																						.collect(Collectors.groupingBy(date -> date, Collectors.counting()));

						// 예약 max 값 확인
						Long maxCount = countMap.values().stream()
														.max(Long::compare)
														.orElse(0L);

						// 기간 내 초과예약 존재시 얼리리턴
						if (maxCount >= 방개수) {
								// [2025-12-11:3, 2025-12-13:1] 이런식의 entrySet
								// 초과된 일자 첫번째것만 리턴
								LocalDate overDate = countMap.entrySet()
																.stream()
																.filter(item -> item.getValue() >= 방개수)
																.map(Map.Entry::getKey)
																.findFirst().orElseThrow();

								throw new OverReservationException(String.format("방개수 초과 - 초과된 일자: %s", overDate));
						}
				}

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
