package com.example.hotel_back.hotel;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.common.util.RandomNumberUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class HotelServiceTests {

    @Autowired
    private HotelRepository hotelRepository;

    private int 테스트식별자 = LocalTime.now().getSecond();

    @Test
    @DisplayName("호텔 추가 테스트")
    public void insertHotelTest() {
        // given
        List<Hotel> hotels = HotelTestFixture.createHotels(1000);

        // when
        hotelRepository.saveAll(hotels);

        // then 저장된다

    }

    @Test
    public void deleteTest() {
        hotelRepository.deleteById(2L);
    }
}
