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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        String email = "kevin11@naver.com";
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
        String email = "kevin43@naver.com";
        String hotelName = "강남구 호텔 1";
        Long 객실ID = 2L;

        User foundUser = userRepository.findByEmail(email).orElseThrow();
        OwnHotel ownHotel = ownHotelRepository.findByOwnHotelId(객실ID);

        //
        LocalDate startDate = LocalDate.of(2025, 11, 17);
        LocalDate endDate = LocalDate.of(2025, 11, 22);

        // 해당날짜범위의 객실대상 예약내역 확인
        // 객실 대상 개수와 겹치는 예약날짜 개수 확인
        // ex) 객실이 5개인데 범위의 날짜가 5개 이상인 경우 각각 날짜 체크
        List<Reservation> list = reservationRepository.findAllByOwnHotelAndDate(ownHotel, startDate, endDate);
        Integer 방개수 = ownHotel.getCountRoom();


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
                .startDate(LocalDate.of(2025,11,17))
                .endDate(LocalDate.of(2025,11,22))
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
        Long hotelId =  1L;
        LocalDate 조회시작일 = LocalDate.of(2025, 11, 18);
        LocalDate 조회종료일 = LocalDate.of(2025, 11, 19);

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

        // ✅ 2. 룸타입 가져오기
        List<Map<String, Integer>> roomTypeList = new ArrayList<>();

        for (OwnHotel oh : ownHotelList) {
            roomTypeList.add(
                Map.of(oh.getRoomType(), oh.getCountRoom())
            );
        }

        // ✅ 3. 시작 ~ 종료일 내 해당 호텔 예약 리스트 가져오기
        List<Reservation> reservedList = reservationRepository.findAllByHotelIdAndDate(hotelId, 조회시작일, 조회종료일);

        // ⛳ then

    }

}
