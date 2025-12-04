package com.example.hotel_back.hotel.service;

import com.example.hotel_back.hotel.dto.*;
import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.entity.HotelType;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.ownhotel.dto.OwnHotelDTO;
import com.example.hotel_back.ownhotel.entity.OwnHotel;
import com.example.hotel_back.ownhotel.repository.OwnHotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class HotelService {

	private final HotelRepository hotelRepository;
	private final OwnHotelRepository ownHotelRepository;

	public List<HotelMarker> getHotelMarkers(HotelMarkerRequest request) {
		List<HotelMarker> list =
						hotelRepository.findHotelsInBounds(
										request.getStartLat(), request.getEndLat(),
										request.getStartLng(), request.getEndLng()
						);

		return list;
	}

	public Page<HotelSearchResponse> getSearch(String keyword, HotelType type, Pageable pageable) {
		Page<HotelSearchProjection> page = hotelRepository.findAllHotelsByKeyword(keyword, type, pageable);

		List<String> randomImages = List.of(
						"/uploads/randomHotels/그랜드하얏트.jpg",
						"/uploads/randomHotels/글래드여의도.jpg",
						"/uploads/randomHotels/롯데호텔.jpg",
						"/uploads/randomHotels/서머셋.jpg",
						"/uploads/randomHotels/신라스테이.jpg",
						"/uploads/randomHotels/켄싱턴.jpg",
						"/uploads/randomHotels/페어필드.jpg",
						"/uploads/randomHotels/나인트리.jpg",
						"/uploads/randomHotels/리베라.jpg",
						"/uploads/randomHotels/비스타.jpg",
						"/uploads/randomHotels/소테츠.jpg",
						"/uploads/randomHotels/안토.jpg",
						"/uploads/randomHotels/올림피아.jpg",
						"/uploads/randomHotels/호텔인나인.jpg",
						"/uploads/randomHotels/서머셋.jpg",
						"/uploads/randomHotels/롯데호텔.jpg",
						"/uploads/randomHotels/메리어트.jpg"
		);

		return page.map(p -> {
			int hotelRandomNumber = ThreadLocalRandom.current().nextInt(0, randomImages.size() - 1);

			return HotelSearchResponse.builder()
							.hotelId(p.getHotelId())
							.hotelName(p.getHotelName())
							.businessNumber(p.getBusinessNumber())
							.registNumber(p.getRegistNumber())
							.location(p.getLocation())
							.hotelType(p.getHotelType())
							.location(p.getLocation())
							.hotelPictureList(List.of(randomImages.get(hotelRandomNumber)))
							.ownHotelList(ownHotelRepository.findAllByHotel_HotelId(p.getHotelId())
											.stream()
											.map(ownHotel -> {
												int randomNumber = ThreadLocalRandom.current().nextInt(0, randomImages.size() - 1);

												var dto = ownHotel.toDTO();
												dto.setPictureList(List.of(randomImages.get(randomNumber)));
												return dto;
											})
											.toList()
							)
							.build();
		});

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

	public Page<HotelDTO> getHotelsByLocation(Pageable pageable, String location) {
		Page<Hotel> page = hotelRepository.findByLocation(pageable, location);
		Page<HotelDTO> dtoPage = page.map(item -> {

			return HotelDTO.builder()
							.hotelId(item.getHotelId())
							.location(item.getLocation())
							.name(item.getName())
							.tel(item.getTel())
							.owner(item.getOwner())
							.build();
		});

		return dtoPage;
	}

	public HotelDetailResponse getDetail(Long hotelId) {
		HotelDetailProjection res = hotelRepository.findHotelDetailByHotelId(hotelId);
		Long hotelIdForFind = res.getHotelId();

		List<OwnHotel> ownHotelList = ownHotelRepository.findAllByHotel_HotelId(hotelIdForFind);
		List<OwnHotelDTO> list = ownHotelList.stream().map(OwnHotelDTO::new).toList();

		return HotelDetailResponse.builder()
						.hotelId(res.getHotelId())
						.hotelName(res.getHotelName())
						.businessNumber(res.getBusinessNumber())
						.registNumber(res.getRegistNumber())
						.tel(res.getTel())
						.pictureUrl(res.getPictureUrl())
						.location(res.getLocation())
						.latitude(res.getLatitude())
						.longitude(res.getLongitude())
						.ownHotelList(list)
						.build();
	}
}
