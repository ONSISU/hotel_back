package com.example.hotel_back.hotel.controller;

import com.example.hotel_back.common.exception.hotel.NoWithinMarkerRange;
import com.example.hotel_back.hotel.dto.HotelMarker;
import com.example.hotel_back.hotel.dto.HotelMarkerRequest;
import com.example.hotel_back.hotel.service.HotelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotel")
@Tag(name = "Hotels", description = "Hotel management endpoints")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/markers")
    public List<HotelMarker> getHotelMarkers(
            @RequestParam Double startLat,
            @RequestParam Double endLat,
            @RequestParam Double startLng,
            @RequestParam Double endLng,
            @RequestParam Integer zoomLevel
    ) {
        HotelMarkerRequest request = new HotelMarkerRequest(
                startLat, endLat,startLng, endLng, zoomLevel
        );

        Boolean is마커노출범위 = 13 <= request.getZoomLevel() && request.getZoomLevel() <= 21;
        Boolean is클러스터노출범위 = 0 <= request.getZoomLevel() && request.getZoomLevel() <= 12;

        if (!is마커노출범위 && !is클러스터노출범위) {
            throw new NoWithinMarkerRange("마커 노출 범위가 아닙니다.");
        }

        List<HotelMarker> list = hotelService.getHotelMarkers(request);
        return list;
    }
}
