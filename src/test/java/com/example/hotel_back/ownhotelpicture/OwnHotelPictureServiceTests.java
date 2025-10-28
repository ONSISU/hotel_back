package com.example.hotel_back.ownhotelpicture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.ownhotel.entity.OwnHotel;
import com.example.hotel_back.ownhotel.entity.OwnHotelPicture;
import com.example.hotel_back.ownhotel.repository.OwnHotelPictureRepository;
import com.example.hotel_back.ownhotel.repository.OwnHotelRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@Log4j2
public class OwnHotelPictureServiceTests {

  @Autowired
  private OwnHotelPictureRepository ownHotelPictureRepository;

  @Autowired
  private OwnHotelRepository ownHotelRepository;

  @Autowired
  private HotelRepository hotelRepository;

  private int 테스트시간 = LocalTime.now().getMinute();

  /**
   * 주제: A호텔에 등록된 소유호텔 사진 등록
   * 
   * Given: A호텔 이미 등록상태, 소유호텔도 등록상태.
   * When: 소유호텔에 이미지 등록할 때
   * Then: 소유호텔에 이미지가 등록된다
   */
  @Test
  @Transactional
  @Commit
  public void insertOwnHotelPictureTest() {
    // given
    Hotel hotel = hotelRepository.findByName("별이 빛나 호텔16").orElseThrow();
    Long hotelId = hotel.getHotelId();

    OwnHotel ownHotel = ownHotelRepository.findByHotel(hotel).orElseThrow();

    // when
    OwnHotelPicture p = OwnHotelPicture.builder()
        .picutreName("room1%s.png".formatted(테스트시간))
        .picutreUrl("https://tomhoon.my.s3.bucket/")
        .ownHotel(ownHotel)
        .build();

    OwnHotelPicture savedOwnHotelPicture = ownHotelPictureRepository.save(p);

    // then
    log.info("savedOwnHotelPicture: {} ", savedOwnHotelPicture.toString());
    assertThat(savedOwnHotelPicture.getOwnHotel().getHotel().getName().equals("별이 빛나 호텔16"));

  }

  /**
   * 주제: A호텔에 등록된 특정 소유호텔의 사진 조회
   * 
   * Given: ✅ A호텔 이미 등록상태, ✅ 소유호텔 등록, ✅ 사진 등록.
   * When: 소유호텔의 이미지를 조회할 때
   * Then: 소유호텔에 이미지를 조회한다
   */
  @Test
  @DisplayName("특정 소유호텔의 사진 조회 테스트")   
  @Transactional
  public void findOwnHotelPictureByOwnHotelTest() {
    // given
    Hotel hotel = hotelRepository.findByName("별이 빛나 호텔16").orElseThrow();
    OwnHotel ownHotel = ownHotelRepository.findByHotel(hotel).orElseThrow();

    // when
    List<OwnHotelPicture> list = ownHotelPictureRepository.findByOwnHotel_OwnHotelId(ownHotel.getOwnHotelId());

    // then
    log.info("ownHotelPicture: {} ", list.size());
    Boolean 하나라도있니 = list.stream()
    .anyMatch(item -> item.getOwnHotel().getHotel().getName().equals("별이 빛나 호텔16"));
    assertThat(하나라도있니).isTrue();
  }

    /**
   * 주제: A호텔에 등록된 특정 소유호텔의 기존 사진에 더 추가
   * 
   * Given: ✅ A호텔 이미 등록상태, ✅ 소유호텔 등록, ✅ 사진 등록.
   * When: 소유호텔의 존재하는 이미지 외 더 추가
   * Then: 소유호텔에 존재하는 이미지 외 더 추가된다
   */
  @Test
  @DisplayName("특정 소유호텔의 사진 조회 테스트")   
  @Transactional
  public void insertExtraOwnHotelPictureByOwnHotelTest() {
    // given
    Hotel hotel = hotelRepository.findByName("별이 빛나 호텔16").orElseThrow();
    OwnHotel ownHotel = ownHotelRepository.findByHotel(hotel).orElseThrow();

    // when
    OwnHotelPicture p = OwnHotelPicture.builder()
        .picutreName("room_extra_%s.png".formatted(테스트시간))
        .picutreUrl("https://tomhoon.my.s3.bucket/extra")
        .ownHotel(ownHotel)
        .build();

    int beforeSave사진개수 = ownHotelPictureRepository.findByOwnHotel_OwnHotelId(ownHotel.getOwnHotelId()).size();
    log.info("beforeSave사진개수::: {} ", beforeSave사진개수);
    
    OwnHotelPicture savedOwnHotelPicture = ownHotelPictureRepository.save(p);
    int afterSave사진개수 = ownHotelPictureRepository.findByOwnHotel_OwnHotelId(ownHotel.getOwnHotelId()).size();
    log.info("afterSave사진개수::: {} ", afterSave사진개수);

    // then
    log.info("savedOwnHotelPicture: {} ", savedOwnHotelPicture.toString());
    assertThat(savedOwnHotelPicture.getOwnHotel().getHotel().getName().equals("별이 빛나 호텔16"));

  }
}
