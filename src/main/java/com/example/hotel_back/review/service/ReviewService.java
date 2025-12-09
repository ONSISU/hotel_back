package com.example.hotel_back.review.service;

import com.example.hotel_back.common.exception.reservation.isNotValidTokenException;
import com.example.hotel_back.common.exception.review.NoReservedHistory;
import com.example.hotel_back.common.util.JwtUtil;
import com.example.hotel_back.reservation.entity.Reservation;
import com.example.hotel_back.reservation.repository.ReservationRepository;
import com.example.hotel_back.review.dto.ReviewRequest;
import com.example.hotel_back.review.dto.ReviewResponse;
import com.example.hotel_back.review.entity.Review;
import com.example.hotel_back.review.repository.ReviewRepository;
import com.example.hotel_back.user.entity.User;
import com.example.hotel_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final ReservationRepository reservationRepository;
	private final JwtUtil jwtUtil;

	public ReviewResponse insertReview(String token, ReviewRequest reviewRequest) {
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}

		Boolean isValid = jwtUtil.validateToken(token);

		if (!isValid) {
			throw new isNotValidTokenException("유효한 토큰이 아닙니다.");
		}

		String email = (String) jwtUtil.getClaims(token).get("email");
		User user = userRepository.findByEmail(email).orElseThrow();
		// 1. 해당 호텔에 예약 기록을 조회
		List<Reservation> list = reservationRepository.findAllByUserId(user.getUserId());
		List<Reservation> filtered = list.stream()
						.filter(item -> item.getHotel().getHotelId().equals(reviewRequest.getHotelId()))
						.toList();

		if (filtered.isEmpty()) {
			throw new NoReservedHistory("예약된 내역이 없습니다.");
		}

		// 2. 존재하는 경우 insert
		Review review = Review.builder()
						.score(reviewRequest.getScore())
						.content(reviewRequest.getContent())
						.hotel(filtered.get(0).getHotel())
						.user(filtered.get(0).getUser())
						.build();

		Review result = reviewRepository.save(review);
		return ReviewResponse.builder()
						.reviewId(result.getReview_id())
						.content(result.getContent())
						.build();
	}

	public List<ReviewResponse> getReview(String token, ReviewRequest request) {
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}

		Boolean isValid = jwtUtil.validateToken(token);

		if (!isValid) {
			throw new isNotValidTokenException("유효한 토큰이 아닙니다.");
		}

		String email = (String) jwtUtil.getClaims(token).get("email");
		User user = userRepository.findByEmail(email).orElseThrow();

		List<Review> list = reviewRepository.findAllByUser(user);

		List<ReviewResponse> mappedList = list.stream().map(item -> {
			return ReviewResponse.builder()
							.content(item.getContent())
							.reviewId(item.getReview_id())
							.build();
		}).toList();

		return mappedList;
	}

}
