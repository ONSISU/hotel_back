package com.example.hotel_back.reservation;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.reservation.entity.Reservation;
import com.example.hotel_back.reservation.repository.ReservationRepository;
import com.example.hotel_back.user.entity.User;
import com.example.hotel_back.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReservationServiceTests {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    public void insertReservationTest1() {
        // 유저가 호텔을 예약한다. (기본값인 오늘날짜, 1박2일로)
        String email = "tom51@naver.com";
        String hotelName = "별이 빛나 호텔5";

        User foundUser = userRepository.findByEmail(email).orElseThrow();
        Hotel hotel = hotelRepository.findByName(hotelName).orElseThrow();

        Reservation reservation = Reservation.builder()
                .hotel(hotel)
                .user(foundUser)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);
        assertThat(savedReservation.getUser().getEmail().equals(email));
    }
    
    @Test
    public void insertReservationTest2() {
        // 유저가 호텔을 예약한다. (2명이서 2박3일로 11월 1일부터)
        String email = "tom51@naver.com";
        String hotelName = "별이 빛나 호텔5";

        User foundUser = userRepository.findByEmail(email).orElseThrow();
        Hotel hotel = hotelRepository.findByName(hotelName).orElseThrow();

        Reservation reservation = Reservation.builder()
                .startDate(LocalDateTime.of(2025,11,1,12,0))
                .endDate(LocalDateTime.of(2025,11,3,12,0))
                .personCount(2)
                .duration(2)
                .hotel(hotel)
                .user(foundUser)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);
        assertThat(savedReservation.getUser().getEmail().equals(email));
    }
}
