package com.example.hotel_back.bookmark.repository;

import com.example.hotel_back.bookmark.entity.BookMark;
import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    Optional<List<BookMark>> findByUser(User user);
    Optional<List<BookMark>> findByHotel(Hotel hotel);
}
