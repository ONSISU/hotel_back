package com.example.hotel_back.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel_back.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
  List<Notice> findByOwnHotel_OwnHotelId(Long ownHotelId);
}
