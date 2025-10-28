package com.example.hotel_back.review;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.review.entity.Review;
import com.example.hotel_back.review.entity.ReviewPicture;
import com.example.hotel_back.review.repository.ReviewPictureRepository;
import com.example.hotel_back.review.repository.ReviewRepository;
import com.example.hotel_back.user.entity.User;
import com.example.hotel_back.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReviewPicutreTest {

    @Autowired
    private ReviewPictureRepository reviewPictureRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void insertReviewWithPictureTest() {
        String userEmail = "kevin42@naver.com";
        String hotelName = "별이 빛나 호텔";

        Hotel hotel = hotelRepository.findByName(hotelName).orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Review review = Review.builder()
                .score(4.4)
                .content("쌈빡하이 좋네예")
                .hotel(hotel)
                .user(user)
                .build();

        reviewRepository.save(review);

        ReviewPicture reviewPicture = ReviewPicture.builder()
                .pictureName("리뷰사진1.png")
                .picturePath("/uploads/")
                .review(review)
                .build();

        ReviewPicture result = reviewPictureRepository.save(reviewPicture);

        Long 리뷰사진아이디 = result.getReviewPicture_id();
        assertThat(리뷰사진아이디 > 0);
    }
}


