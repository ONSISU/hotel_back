package com.example.hotel_back.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelMarker {

    private Long hotelId;
    private String hotelName;

    private Double latitude;

    private Double longitude;

}
