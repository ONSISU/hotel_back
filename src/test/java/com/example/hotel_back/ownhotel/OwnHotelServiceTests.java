package com.example.hotel_back.ownhotel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.ownhotel.entity.OwnHotel;
import com.example.hotel_back.ownhotel.repository.OwnHotelRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
public class OwnHotelServiceTests {

	@Autowired
	private OwnHotelRepository ownHotelRepository;

	@Autowired
	private HotelRepository hotelRepository;

	/**
	 * 주제: A호텔에 소유호텔 등록
	 * <p>
	 * Given: A호텔 이미 등록상태
	 * When: A호텔에 소유호텔을 추가할 때
	 * Then: A호텔에 소유호텔을 추가된다
	 */
	@Test
	@Transactional
	@Commit
	public void insertOwnHotelTest() {
		List<Hotel> list = hotelRepository.findAll();

		// given
//		Hotel h = hotelRepository.findById(1L).orElseThrow();
		List<OwnHotel> ownHotelList = new ArrayList<>();

		int SIZE = 10;

		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < Math.min(SIZE, list.size()); j++) {
				OwnHotel ownHotel = OwnHotelTestFixture.createOwnHotel(j, list.get(i));
				ownHotelRepository.save(ownHotel);
			}
		}
//
//		// 체크인,아웃시간, 수용인원, 기본값으로 설정
//		// 스탠다드형 10개짜리 소유
//		OwnHotel ownHotel = OwnHotel.builder()
//						.price(100000L)
//						.roomType("싱글")
//						.roomName("Single Size Room")
//						.countRoom(2)
//						.hotel(h)
//						.build();
//
//		// when
//		OwnHotel savedOwnHotel = ownHotelRepository.save(ownHotel);
//
//		// then
//		log.info("savedOwnHotel:{} ", savedOwnHotel.toString());
//		assertThat(savedOwnHotel.getRoomType()).isEqualTo("싱글");
	}

}
