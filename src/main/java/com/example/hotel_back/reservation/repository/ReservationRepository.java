package com.example.hotel_back.reservation.repository;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.reservation.entity.Reservation;
import com.example.hotel_back.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<List<Reservation>> findByHotelAndUser(Hotel hotel, User user);
}
