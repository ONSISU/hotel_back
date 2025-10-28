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
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ReviewReply {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewReplyId;

    @Column(columnDefinition = "TINYINT(1) COMMENT '삭제 여부'")
    @Builder.Default
    private Boolean isDel = false;

    @Column(length = 50)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "review_id")
    private Review review;
}
