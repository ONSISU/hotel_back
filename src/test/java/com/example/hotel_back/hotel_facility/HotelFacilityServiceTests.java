package com.example.hotel_back.hotel_facility;

import com.example.hotel_back.facility.entity.Facility;
import com.example.hotel_back.facility.repository.FacilityRepository;
import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.hotel_facility.entity.HotelFacility;
import com.example.hotel_back.hotel_facility.repository.HotelFacilityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HotelFacilityServiceTests {

	@Autowired
	private HotelFacilityRepository hfRepository;

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private FacilityRepository facilityRepository;

	@Test
	@DisplayName("호텔 부대시설 추가")
	public void insertHotelFacility() {
		// given1: 호텔 엔티티 생성
		Hotel hotel = hotelRepository.findAll().get(0);

		// given2: 부대시설 엔티티 생성
		Facility facility = facilityRepository.findAll().get(0);

		// when: HotelFacility 생성
		HotelFacility hotelFacility = HotelFacility.builder()
						.hotel(hotel)
						.facility(facility)
						.price(10000)
						.isFree(false)
						.build();

		// then
		hfRepository.save(hotelFacility);
	}

}
