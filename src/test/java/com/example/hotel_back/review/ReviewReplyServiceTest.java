package com.example.hotel_back.review;

import com.example.hotel_back.review.entity.Review;
import com.example.hotel_back.review.entity.ReviewReply;
import com.example.hotel_back.review.repository.ReviewReplyRepository;
import com.example.hotel_back.review.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReviewReplyServiceTest {

    @Autowired
    private ReviewReplyRepository reviewReplyRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertReviewReplyTest() {
        Review r = reviewRepository.findById(1L).orElseThrow();
        ReviewReply reply = ReviewReply.builder()
                .review(r)
                .build();

        ReviewReply savedReviewReply = reviewReplyRepository.save(reply);

        assertThat(savedReviewReply.getReview().getReview_id().equals(1L));
    }
}
