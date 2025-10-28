package com.example.hotel_back.review.repository;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.review.entity.Review;
import com.example.hotel_back.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findAllByUser(User user);
    Optional<List<Review>> findAllByHotel(Hotel hotel);
}
