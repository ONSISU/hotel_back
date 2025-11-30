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
		Page<HotelSearchProjection> prjList = hotelRepository.findAllByKeyword(keyword, type, pageable);

		Page<HotelSearchResponse> list = prjList
						.map(i -> HotelSearchResponse.builder()
										.hotelId(i.getHotelId())
										.hotelType(i.getHotelType())
										.price(i.getPrice())
										.location(i.getLocation())
										.build());

		for (HotelSearchResponse r : list) {
			Long hotelId = r.getHotelId();
			Hotel h = hotelRepository.findById(hotelId).orElseThrow();
			List<OwnHotelDTO> ownHotelList = ownHotelRepository.findAllByHotel(h)
							.stream()
							.map(i -> OwnHotelDTO.builder()
											.ownHotelId(i.getOwnHotelId())
											.price(i.getPrice())
											.countRoom(i.getCountRoom())
											.checkInTime(i.getCheckInTime())
											.checkOutTime(i.getCheckOutTime())
											.roomType(i.getRoomType())
											.roomName(i.getRoomName())
											.maxPerson(i.getMaxPerson())
											.minPerson(i.getMinPerson())
											.createdAt(i.getCreatedAt())
											.updatedAt(i.getUpdatedAt())
											.build())
							.toList();

			r.setOwnHotelList(ownHotelList);
		}

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
						.pictureUrl(res.getPictureUrl())
						.location(res.getLocation())
						.latitude(res.getLatitude())
						.longitude(res.getLongitude())
						.ownHotelList(list)
						.build();
	}
}
