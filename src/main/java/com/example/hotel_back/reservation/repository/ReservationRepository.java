package com.example.hotel_back.reservation.repository;

import com.example.hotel_back.ownhotel.entity.OwnHotel;
import com.example.hotel_back.reservation.entity.Reservation;
import com.example.hotel_back.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
		Optional<List<Reservation>> findByOwnHotelAndUser(OwnHotel ownHotel, User user);

		// ✅ 호텔 기간 내 예약 리스트
		@Query(
										"""
																		    SELECT r FROM Reservation r
																		    WHERE r.hotel.hotelId = :hotelId
																		      AND r.startDate < :end
																		      AND r.endDate > :start
																		"""
		)
		List<Reservation> findAllByHotelIdAndDate(
										@Param("hotelId") Long hotelId,
										@Param("start") LocalDate start,
										@Param("end") LocalDate end
		);

		// ✅ 객실 기간 내에 예약된 모든 리스트
		@Query(
										"""
																		    SELECT r from Reservation r
																		    WHERE r.ownHotel = :ownHotel
																		      AND r.startDate < :end
																		      AND r.endDate > :start
																		"""
		)
		List<Reservation> findAllByOwnHotelAndDate(
										@Param("ownHotel") OwnHotel ownHotel,
										@Param("start") LocalDate start,
										@Param("end") LocalDate end
		);

		// ✅ 유저의 예약 모든정보
		@Query("""
										SELECT r FROM Reservation r
										WHERE r.user.userId = :userId
										""")
		List<Reservation> findAllByUserId(@Param("userId") Long userId);
}
