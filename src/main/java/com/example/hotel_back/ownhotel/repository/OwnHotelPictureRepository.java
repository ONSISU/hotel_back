package com.example.hotel_back.ownhotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel_back.ownhotel.entity.OwnHotelPicture;

public interface OwnHotelPictureRepository extends JpaRepository<OwnHotelPicture, Long>{  
  List<OwnHotelPicture> findByOwnHotel_OwnHotelId(Long ownHotelId);
}
