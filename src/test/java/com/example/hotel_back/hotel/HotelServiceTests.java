package com.example.hotel_back.hotel;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.util.RandomNumberUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

@SpringBootTest
public class HotelServiceTests {

    @Autowired
    private HotelRepository hotelRepository;

    private int 테스트식별자 = LocalTime.now().getSecond();

    @Test
    public void insertHotelTest() {


        Hotel hotel = Hotel.builder()
                .location("서울시 강서구 방화동%s".formatted(테스트식별자))
                .name("별이 빛나 호텔%s".formatted(테스트식별자))
                .tel("07077777777")
                .owner("김명서%s".formatted(테스트식별자))
                .region("서울시%s".formatted(테스트식별자))
                .businessNumber(RandomNumberUtil.get랜덤사업자번호()) //10자리
                .registNumber(RandomNumberUtil.get판매업자신고번호()) //15자리
                .build();

        hotelRepository.save(hotel);
    }

    @Test
    public void deleteTest() {
        hotelRepository.deleteById(2L);
    }
}
