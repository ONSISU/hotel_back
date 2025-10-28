package com.example.hotel_back.payment;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.payment.entity.Payment;
import com.example.hotel_back.payment.entity.PaymentType;
import com.example.hotel_back.payment.repository.PaymentRepository;
import com.example.hotel_back.reservation.entity.Reservation;
import com.example.hotel_back.reservation.repository.ReservationRepository;
import com.example.hotel_back.user.entity.User;
import com.example.hotel_back.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
public class PaymentServiceTests {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    public void insertPayment() {
        // 11월 1일에 예약한 호텔
        // 전체금액 카드결제

        // 1. tom이 별이빛나 호텔 예약일자 조회
        String email = "tom51@naver.com";
        String hotelName = "별이 빛나 호텔5";

        User user = userRepository.findByEmail(email).orElseThrow();
        Hotel hotel = hotelRepository.findByName(hotelName).orElseThrow();

        List<Reservation> reservedList = reservationRepository.findByHotelAndUser(hotel, user).orElseThrow();

        Reservation targetReservation = null;

        // 2. 11월 1일로 예약한 reservation 찾기
        for (Reservation r : reservedList) {
            String 확인날짜 = "2025-11-01";
            String reservedDate = r.getStartDate()
                    .toLocalDate()
                    .toString();

            if (확인날짜.equals(reservedDate)) {
                targetReservation = r;
                break;
            }
        }

        Payment payment = Payment.builder()
                .reservation(targetReservation)
                .paymentType(PaymentType.CARD)
                .build();

    }
}
