package com.example.hotel_back.review.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ReviewPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewPicture_id;

    @Column(length = 20)
    private String pictureName;

    @Column(length = 20)
    private String picturePath;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "review_id")
    private Review review;
}