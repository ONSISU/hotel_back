package com.example.hotel_back.review.controller;

import com.example.hotel_back.review.dto.ReviewRequest;
import com.example.hotel_back.review.dto.ReviewResponse;
import com.example.hotel_back.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping("/review")
	public ReviewResponse insertReview(@RequestHeader("Authorization") String token, @RequestBody ReviewRequest request) {
		return reviewService.insertReview(token, request);
	}

	@GetMapping("/review")
	public List<ReviewResponse> getReview(@RequestHeader("Authorization") String token, @RequestBody ReviewRequest request) {
		return reviewService.getReview(token, request);
	}


}
