package com.example.hotel_back.hotel.repository;

import com.example.hotel_back.hotel.dto.HotelDetailProjection;
import com.example.hotel_back.hotel.dto.HotelMarker;
import com.example.hotel_back.hotel.dto.HotelSearchProjection;
import com.example.hotel_back.hotel.dto.HotelSearchResponse;
import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.entity.HotelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Query(
					value = """
									    SELECT 
									        h.hotelId AS hotelId,
									        h.name AS hotelName,
									        h.businessNumber AS businessNumber,
									        h.registNumber AS registNumber,
									        h.hotelType AS hotelType,
									        MIN(o.price) AS minPrice,
									        MAX(o.price) AS maxPrice,
									        h.location AS location
									    FROM Hotel h
									    JOIN OwnHotel o ON o.hotel = h
									    WHERE (
									      :keyword IS NULL OR h.name LIKE CONCAT('%', :keyword, '%')
									     OR h.location LIKE CONCAT('%', :keyword, '%'))
									     AND (:type IS NULL OR h.hotelType = :type)
									    GROUP BY 
									        h.hotelId, 
									        h.name, 
									        h.businessNumber,
									        h.registNumber,
									        h.hotelType,
									        h.location
									""",
					countQuery = """
									    SELECT COUNT(DISTINCT h.hotelId)
									    FROM Hotel h
									    JOIN OwnHotel o ON o.hotel = h
									"""
	)
	Page<HotelSearchProjection> findAllHotelsByKeyword(
					@Param("keyword") String keyword,
					@Param("type") HotelType type,
					Pageable pageable
	);


	@Query("""
					
					""")
	Page<Hotel> findByLocation(Pageable pageable, @Param("location") String location);


	@Query("""
					SELECT 
						h.hotelId as hotelId,
						hp.pictureUrl as pictureUrl,
						h.name as hotelName,
						h.tel as tel,
						h.businessNumber as businessNumber,
						h.registNumber as registNumber,
						h.location as location,
						h.latitude as latitude,
						h. longitude as longitude,
						COUNT(oh.roomType) as roomCounts
					FROM Hotel h
					LEFT JOIN OwnHotel oh
					ON h.hotelId = oh.hotel.hotelId
					LEFT JOIN HotelPicture hp
					ON hp.hotel.hotelId = h.hotelId
					WHERE h.hotelId = :hotelId
					GROUP BY h.hotelId
					""")
	HotelDetailProjection findHotelDetailByHotelId(@Param("hotelId") Long hotelId);
}
