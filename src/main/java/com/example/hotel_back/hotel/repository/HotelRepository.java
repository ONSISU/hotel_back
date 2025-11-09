package com.example.hotel_back.hotel.repository;

import com.example.hotel_back.hotel.dto.HotelMarker;
import com.example.hotel_back.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByName(String name);

    @Query("""
            SELECT  new com.example.hotel_back.hotel.dto.HotelMarker(h.id, h.name, h.latitude, h.longitude) 
            FROM Hotel h WHERE h.latitude BETWEEN :startLat AND :endLat
            AND h.longitude BETWEEN :startLng AND :endLng
            """
    )
    List<HotelMarker> findHotelsInBounds(
            @Param("startLat") Double startLat,
            @Param("endLat") Double endLat,
            @Param("startLng") Double startLng,
            @Param("endLng") Double endLng
    );
}
