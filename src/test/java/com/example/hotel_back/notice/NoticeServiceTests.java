package com.example.hotel_back.notice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.notice.entity.Notice;
import com.example.hotel_back.notice.repository.NoticeRepository;
import com.example.hotel_back.ownhotel.entity.OwnHotel;
import com.example.hotel_back.ownhotel.repository.OwnHotelRepository;

import lombok.extern.log4j.Log4j2;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
public class NoticeServiceTests {

  @Autowired
  private NoticeRepository noticeRepository;
  
  @Autowired
  private HotelRepository hotelRepository;

  @Autowired
  private OwnHotelRepository ownHotelRepository;

  /**
   * 주제: A호텔에 소유호텔에 공지추가
   * 
   * Given: ✅ A호텔 이미 등록상태, ✅ 소유호텔도 등록상태.
   * When: A호텔에 소유호텔에 공지 추가할 때
   * Then: A호텔에 소유호텔에 공지 추가된다.
   */
  @Test
  public void insertNoticeTest() {
    // given
    Hotel hotel = hotelRepository.findByName("별이 빛나 호텔1").orElseThrow();
    Long hotelId = hotel.getHotelId();
    
    OwnHotel ownHotel = ownHotelRepository.findByHotel(hotel).orElseThrow();  

    // when
    Notice n = Notice.builder()
    .content("2025년 12월 31일 까지 30% 할인이벤트 진행 중입니다")
    .usageGuide("주차는 객실당 하나만 가능합니다. 참고 바랍니다.")
    .introduction("모든 객실이 오션뷰")
    .ownHotel(ownHotel)
    .build();

    Notice savedNotice = noticeRepository.save(n);

    // then
    log.info("savedNotice ::: {} ", savedNotice);
    assertThat(savedNotice.getOwnHotel().getHotel().getName().equals("별이 빛나 호텔1"));

  }
}
