package com.example.hotel_back.reservation;

import com.example.hotel_back.common.exception.hotel.NoOwnHotelList;
import com.example.hotel_back.common.exception.hotel.NotFoundHotel;
import com.example.hotel_back.common.exception.reservation.OverReservationException;
import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.ownhotel.entity.OwnHotel;
import com.example.hotel_back.ownhotel.repository.OwnHotelRepository;
import com.example.hotel_back.reservation.entity.Reservation;
import com.example.hotel_back.reservation.repository.ReservationRepository;
import com.example.hotel_back.user.entity.User;
import com.example.hotel_back.user.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
public class ReservationServiceTests {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private OwnHotelRepository ownHotelRepository;

	@Test
	public void insertReservationTest1() {
		// 유저가 호텔을 예약한다. (기본값인 오늘날짜, 1박2일로)
		String email = "@naver.com";
		String hotelName = "강남구 호텔 1";
		Long 객실ID = 1L;

		User foundUser = userRepository.findByEmail(email).orElseThrow();

		OwnHotel ownHotel = ownHotelRepository.findByOwnHotelId(객실ID);

		Reservation reservation = Reservation.builder()
						.ownHotel(ownHotel)
						.user(foundUser)
						.hotel(ownHotel.getHotel())
						.build();

		Reservation savedReservation = reservationRepository.save(reservation);
		assertThat(savedReservation.getUser().getEmail().equals(email));
	}

	@Test
	public void insertReservationTest2() {
		// 유저가 호텔을 예약한다. (N박 M일로)
		// 동훈37
		// 명수18
		// 병현1
		String email = "병현1@naver.com";
		String hotelName = "강남구 호텔 1";
		Long 객실ID = 4L;

		User foundUser = userRepository.findByEmail(email).orElseThrow();
		OwnHotel ownHotel = ownHotelRepository.findByOwnHotelId(객실ID);

		LocalDate startDate = LocalDate.of(2025, 12, 2);
		LocalDate endDate = LocalDate.of(2025, 12, 4);

		// 해당객실의 예약일자 데이터 조회
		List<Reservation> list = reservationRepository.findAllByOwnHotelAndDate(ownHotel, startDate, endDate);
		Integer 방개수 = ownHotel.getCountRoom();


		// 예약초과 확인
		if (방개수 <= list.size()) {
			List<LocalDate> fullDateList = new ArrayList<>();

			for (Reservation r : list) {
				int nights = (int) ChronoUnit.DAYS.between(startDate, endDate);
				List<LocalDate> reservedDateList = IntStream.range(0, nights)
								.mapToObj(startDate::plusDays)
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
				throw new OverReservationException("방개수 초과");
			}
		}


		Reservation reservation = Reservation.builder()
						.startDate(startDate)
						.endDate(endDate)
						.personCount(2)
						.duration(2)
						.ownHotel(ownHotel)
						.hotel(ownHotel.getHotel())
						.user(foundUser)
						.build();

		Reservation savedReservation = reservationRepository.save(reservation);
		assertThat(savedReservation.getUser().getEmail().equals(email));
	}

	@Test
	@DisplayName("check available rooms")
	public void getAvailableRooms() {
		// Get RoomTypes List

		// ⛳ given
		Long hotelId = 1L;
		LocalDate 조회시작일 = LocalDate.of(2025, 12, 1);
		LocalDate 조회종료일 = LocalDate.of(2025, 12, 4);

		Optional<Hotel> res = hotelRepository.findById(hotelId);
		if (res.isEmpty()) {
			throw new NotFoundHotel("Not Found Hotel");
		}

		// ⛳️ when
		// ✅ 1. HotelId로 own_hotel 가져오기
		Hotel hotel = res.get();
		List<OwnHotel> ownHotelList = ownHotelRepository.findAllByHotel(hotel);

		if (ownHotelList.isEmpty()) {
			throw new NoOwnHotelList("No Own Hotel Room");
		}


		// ✅ 2. 시작 ~ 종료일 내 해당 호텔 예약 리스트 가져오기
		List<Reservation> reservedList = reservationRepository.findAllByHotelIdAndDate(hotelId, 조회시작일, 조회종료일);

		Map<Long, Integer> ownHotelRestRoomCntMap = new HashMap<>();

		// { 소유객실ID: 예약한 횟수 }
		for (Reservation r1 : reservedList) {

			Long ownHotelId = r1.getOwnHotel().getOwnHotelId();
			Integer reservedCnt = ownHotelRestRoomCntMap.getOrDefault(ownHotelId, 1);
			ownHotelRestRoomCntMap.put(ownHotelId, reservedCnt + 1);
		}

		// ✅ 3. 룸타입 가져오기
		Map<Long, Map<String, Object>> resultOfRoom = new HashMap<>();

		/**
		 {
		 id: {
		 roomType: '',
		 avilableCnt: ''
		 }
		 }
		 */
		for (OwnHotel oh : ownHotelList) {
			resultOfRoom.put(
							oh.getOwnHotelId(), Map.of(
											"roomType", oh.getRoomType(),
											"availableCnt", oh.getCountRoom() - ownHotelRestRoomCntMap.getOrDefault(oh.getOwnHotelId(), 1)
							)
			);
		}

		// return resultOfROom;
		System.out.println(">>>> resultOfRoom " + resultOfRoom);

		// ⛳ then

	}

}
