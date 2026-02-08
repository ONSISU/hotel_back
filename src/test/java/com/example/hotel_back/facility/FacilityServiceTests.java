package com.example.hotel_back.facility;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.hotel_back.facility.entity.Facility;
import com.example.hotel_back.facility.repository.FacilityRepository;
import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;

import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class FacilityServiceTests {

	@Autowired
	private FacilityRepository facilityRepository;

	@Autowired
	private HotelRepository hotelRepository;

	/**
	 * 주제: 부대시설을 등록
	 * <p>
	 * Given: 등록할 부대시설 정보
	 * When: 부대시설 등록할 때
	 * Then: 부대시설이 등록된다
	 */
	@Test
	@DisplayName("부대시설을 등록")
	public void createFacilityTest() {
		// given
		String serviceName = "밥 먹여주기";
		String serviceDetail = "맛있는 밥을 먹여줍니다.";

		Facility f = Facility.builder()
						.serviceName(serviceName)
						.serviceDetail(serviceDetail)
						.build();

		// when
		facilityRepository.save(f);

		// then
		Facility foundFacility = facilityRepository.findByServiceName(serviceName).orElseThrow();
		assertThat(foundFacility.getServiceName().equals(serviceName));
	}

	/**
	 * 주제: 부대시설을 수정
	 * <p>
	 * Given: 이미 등록된 부대시설
	 * When: 부대시설 등록할 때
	 * Then: 부대시설이 수정된다
	 */
	@Test
	@DisplayName("부대시설을 수정")
	public void updateFacilityTest() {

		// given
		Facility facility = facilityRepository.save(Facility.builder()
						.serviceName("Old Service")
						.serviceDetail("Old Detail")
						.build());

		// when
		facility.setServiceName("테스트 부대시설1");
		facility.setServiceDetail("테스트 부대시설1 - 상세내용");
		facilityRepository.save(facility);

		// then
		Facility updatedFacility = facilityRepository.findById(facility.getId()).orElseThrow();
		assertThat(updatedFacility.getServiceName()).isEqualTo("테스트 부대시설1");
		assertThat(updatedFacility.getServiceDetail()).isEqualTo("테스트 부대시설1 - 상세내용");

	}

	/**
	 * 주제: 호텔에 부대시설 등록하기
	 * <p>
	 * Given: 사전 호텔이 등록, 부대시설 5개 이상 등록
	 * When: 호텔에 부대시설 등록할 때
	 * Then: 호텔에 부대시설이 등록된다
	 */
	@Test
	@DisplayName("호텔에 부대시설 등록하기")
	@Transactional
	public void readFacilityTest() {

		// given
		Hotel hotel = hotelRepository.findById(1L).orElseThrow();

		Facility facility1 = facilityRepository.save(Facility.builder()
						.serviceName("수영장")
						.serviceDetail("시원한 수영장")
						.build());
		Facility facility2 = facilityRepository.save(Facility.builder()
						.serviceName("헬스장")
						.serviceDetail("최신식 헬스장")
						.build());
		Facility facility3 = facilityRepository.save(Facility.builder()
						.serviceName("사우나")
						.serviceDetail("편안한 사우나")
						.build());
		Facility facility4 = facilityRepository.save(Facility.builder()
						.serviceName("레스토랑")
						.serviceDetail("맛있는 레스토랑")
						.build());
		Facility facility5 = facilityRepository.save(Facility.builder()
						.serviceName("바")
						.serviceDetail("멋진 바")
						.build());

		List<Facility> facilities = new ArrayList<>(List.of(facility1, facility2, facility3, facility4, facility5));

//    // when
//    hotel.setFacility(facilities);
//    hotelRepository.save(hotel);
//
//    // then
//    Hotel savedHotel = hotelRepository.findById(1L).orElseThrow();
//    assertThat(savedHotel.getFacility().size()).isEqualTo(5);

	}

}
