package com.example.hotel_back.ownhotel.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.ownhotel.entity.OwnHotel;
import org.springframework.data.jpa.repository.Query;

public interface OwnHotelRepository extends JpaRepository<OwnHotel, Long>{
  Optional<OwnHotel> findByHotel(Hotel hotel);

  OwnHotel findByOwnHotelId(Long ownHotelId);

  List<OwnHotel> findAllByHotel(Hotel hotel);
}
