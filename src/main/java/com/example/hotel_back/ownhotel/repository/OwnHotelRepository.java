package com.example.hotel_back.ownhotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.ownhotel.entity.OwnHotel;

public interface OwnHotelRepository extends JpaRepository<OwnHotel, Long>{
  Optional<OwnHotel> findByHotel(Hotel hotel);
}
