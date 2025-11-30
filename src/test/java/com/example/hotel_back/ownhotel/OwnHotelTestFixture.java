package com.example.hotel_back.ownhotel;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.ownhotel.entity.OwnHotel;

import java.util.List;

public class OwnHotelTestFixture {

	public static OwnHotel createOwnHotel(int idx, Hotel hotel) {
		// price
		Long[] priceList = {159000L, 200000L, 190000L, 120000L, 89000L, 234000L, 256000L};

		// countRoom
		Integer[] countRoomList = {
						1, 2, 4, 5, 10
		};

		// roomName
		String[] roomNameList = {
						"디럭스 트윈룸",
						"시티뷰 스위트",
						"오션 브리즈룸",
						"프리미엄 킹룸",
						"가든뷰 더블룸",
						"마운틴뷰 스위트",
						"이그제큐티브 퀸룸",
						"로열 패밀리 스위트",
						"클래식 싱글룸",
						"슈페리어 트윈 디럭스",
						"스카이라인 파노라마 스위트",
						"코지 코너룸",
						"헤리티지 킹 스위트",
						"비즈니스 스튜디오룸",
						"선셋뷰 퀸룸",
						"모던 로프트룸",
						"럭셔리 프레지덴셜 스위트",
						"어반 컴포트 더블룸",
						"호라이즌뷰 킹룸",
						"패밀리 디럭스룸",
						"엘레강트 슈페리어룸",
						"리버사이드 파노라마 스위트",
						"컴포트 트윈 스탠다드",
						"플라자뷰 킹룸",
						"트랜퀼 가든 스위트"
		};

		// roomType
		String[] roomTypeList = {
						"싱글룸",
						"더블룸",
						"트윈룸",
						"트리플룸",
						"패밀리룸",
						"디럭스룸",
						"슈페리어룸",
						"프리미엄룸",
						"스위트룸",
						"주니어 스위트",
						"프레지덴셜 스위트",
						"온돌룸",
						"한실",
						"로프트룸",
						"스탠다드룸",
						"파노라마룸",
						"오션뷰룸",
						"시티뷰룸",
						"가든뷰룸",
						"마운틴뷰룸"
		};

		return OwnHotel.builder()
						.price(priceList[idx % priceList.length])
						.countRoom(countRoomList[idx % countRoomList.length])
						.roomType(roomTypeList[idx % roomTypeList.length])
						.roomName(roomNameList[idx % roomNameList.length])
						.hotel(hotel)
						.build();

	}

}
