package com.example.hotel_back.review;

import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.review.entity.Review;
import com.example.hotel_back.review.repository.ReviewRepository;
import com.example.hotel_back.user.entity.User;
import com.example.hotel_back.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReviewServiceTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    public void insertReviewTest() {
        // 탐훈이 '별이 빛나 호텔'호텔 리뷰
        // TODO: 예약당 한번. 지금은 여러번 되어 이슈.
        String email = "tom@naver.com";
        User foundUser = userRepository.findByEmail(email).orElseThrow();
        Hotel hotel = hotelRepository.findByName("강남구 호텔 1").orElseThrow();

        Review review = Review.builder()
                .score(4)
                .content("여기 나름 괜찮아요")
                .user(foundUser)
                .hotel(hotel)
                .build();

        reviewRepository.save(review);
    }

    @Test
    public void readAllReviewsByUserTest() {

        // 탐훈이 작성한 모든 리뷰확인
        String email = "tom@naver.com";
        User user = userRepository.findByEmail(email).orElseThrow();

        List<Review> list = reviewRepository.findAllByUser(user).orElseThrow();

        for (Review r : list) {
            System.out.println("r >>> " + r);
        }

    }

    @Test
    @Transactional
    public void readAllReviewsByHotelTest() {
        // 별이 빛나 호텔에 기록된 모든 리뷰 확인
        Hotel hotel = hotelRepository.findByName("별이 빛나 호텔").orElseThrow();

        List<Review> list = reviewRepository.findAllByHotel(hotel).orElseThrow();

        for (Review r : list) {
            String email = r.getUser().getEmail();
            System.out.println("email >>> " + email);
            System.out.println("user >>> " + r.getHotel().getName());
        }
    }
}
