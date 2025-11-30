package com.example.hotel_back.hotel;

import com.example.hotel_back.common.util.RandomNumberUtil;
import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.entity.HotelType;

import java.util.ArrayList;
import java.util.List;

public class HotelTestFixture {
	public static List<Hotel> createHotels(int count) {
		return createHotels(count, HotelType.HOTEL);
	}

	public static List<Hotel> createHotels(int count, HotelType type) {
		List<Hotel> hotels = new ArrayList<>();

		Double[][] coordinates = {
						{37.4979, 127.0276}, // 강남구
						{37.5725, 126.9904}, // 종로구
						{37.5515, 126.9241}, // 마포구
						{37.4845, 127.0173}, // 서초구
						{37.5604, 126.9723}, // 중구
						{37.5447, 127.1416}, // 강동구
						{37.5653, 126.8335}, // 강서구
						{37.6548, 127.0758}, // 노원구
						{37.6654, 127.0339}, // 도봉구
						{37.6210, 126.9826}, // 성북구
						{37.5990, 126.9721}, // 동대문구동대문구
						{37.5748, 126.8849}, // 용산구
						{37.5566, 126.8768}, // 서대문구
						{37.5730, 126.9486}, // 중랑구
						{37.6141, 127.0944}, // 광진구
						{37.5385, 127.0730}  // 송파구
		};

		String[] regions = {
						"강남구", "종로구", "마포구", "서초구", "중구",
						"강동구", "강서구", "노원구", "도봉구", "성북구",
						"동대문구", "용산구", "서대문구", "중랑구", "광진구",
						"송파구"
		};

		for (int i = 1; i <= count; i++) {
			int idx = (i - 1) % regions.length;
			Double lat = coordinates[idx][0] + (Math.random() * 0.008);
			Double lng = coordinates[idx][1] + (Math.random() * 0.008);

			hotels.add(Hotel.builder()
							.name(regions[idx] + " 호텔 " + (i / regions.length + 1))
							.location("서울시 " + regions[idx] + " " + i + "번지")
							.latitude(lat)
							.longitude(lng)
							.tel("02-" + String.format("%04d", i % 10000))
							.owner("소유자" + i)
							.businessNumber(RandomNumberUtil.get랜덤사업자번호())
							.registNumber(RandomNumberUtil.get판매업자신고번호())
							.region(regions[idx])
							.hotelType(type)
							.build());
		}

		return hotels;
	}
}