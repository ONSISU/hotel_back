package com.example.hotel_back.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelMarkerRequest {

    private Double startLat;
    private Double startLng;

    private Double endLat;
    private Double endLng;

    private int ZoomLevel;

}
