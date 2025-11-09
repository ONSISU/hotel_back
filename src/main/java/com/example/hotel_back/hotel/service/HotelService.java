package com.example.hotel_back.hotel.service;

import com.example.hotel_back.hotel.dto.HotelMarker;
import com.example.hotel_back.hotel.dto.HotelMarkerRequest;
import com.example.hotel_back.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
