package com.example.hotel_back.reservation.entity;


import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
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
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservation_id;

    /*
        @Column(columnDefinition = "TIMESTAMP") 대신 datTime으로
        TimeStamp 는 2038년까지
        미지정시 type은 dateTime. 9999년까지
    */
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @PrePersist
    public void prePersist() {
        if (this.startDate == null) {
            this.startDate = LocalDateTime.now();
        }
        if (this.endDate == null) {
            this.endDate = this.startDate.plusDays(1);
        }
    }

    // int 대신 Integer. Nullable 처리 가능하도록.
    @Column(columnDefinition = "INT UNSIGNED COMMENT '숙박기간. 1이면 1박2일'")
    @Builder.Default
    private Integer duration = 1;

    @Column(columnDefinition = "INT UNSIGNED")
    @Builder.Default
    private Integer personCount = 1;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
