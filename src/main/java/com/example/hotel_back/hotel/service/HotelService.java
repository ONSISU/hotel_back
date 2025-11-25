package com.example.hotel_back.hotel.service;

import com.example.hotel_back.hotel.dto.HotelDTO;
import com.example.hotel_back.hotel.dto.HotelMarker;
import com.example.hotel_back.hotel.dto.HotelMarkerRequest;
import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {

	private final HotelRepository hotelRepository;

	public List<HotelMarker> getHotelMarkers(HotelMarkerRequest request) {
		List<HotelMarker> list =
						hotelRepository.findHotelsInBounds(
										request.getStartLat(), request.getEndLat(),
										request.getStartLng(), request.getEndLng()
						);

		return list;
	}

	public List<HotelDTO> getSearch(String keyword) {
		List<HotelDTO> list = hotelRepository.findAllByKeyword(keyword)
						.stream()
						.map(i -> HotelDTO.builder()
										.hotelId(i.getHotelId())
										.location(i.getLocation())
										.name(i.getName())
										.tel(i.getTel())
										.owner(i.getOwner())
										.build())
						.toList();

		return list;
	}

	public List<HotelDTO> getPopular() {
		List<Hotel> list = hotelRepository.findAll();
		List<Hotel> sliced = list.subList(0, Math.min(5, list.size()));

		List<String> imageList = new ArrayList<>();
		imageList.add("/uploads/그랜드하얏트.jpg");
		imageList.add("/uploads/글래드여의도.jpg");
		imageList.add("/uploads/롯데호텔.jpg");
		imageList.add("/uploads/서머셋.jpg");
		imageList.add("/uploads/신라스테이.jpg");
		imageList.add("/uploads/켄싱턴.jpg");
		imageList.add("/uploads/페어필드.jpg");

		List<HotelDTO> dtoList = new ArrayList<>();

		for (int i = 0; i < sliced.size(); i++) {
			Hotel h = sliced.get(i);

			dtoList.add(
							HotelDTO.builder()
											.hotelId(h.getHotelId())
											.location(h.getLocation())
											.name(h.getName())
											.tel(h.getTel())
											.owner(h.getOwner())
											.imagePath(imageList.get(i))
											.build()
			);
		}

		return dtoList;
	}
}
