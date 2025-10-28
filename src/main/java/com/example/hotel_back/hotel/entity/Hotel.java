package com.example.hotel_back.hotel.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.hotel_back.facility.entity.Facility;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Hotel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    @Column(length = 20, nullable = false)
    private String location;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String tel;

    @Column(length = 20, nullable = false)
    private String owner;

    @Column(length = 20, nullable = false, unique = true)
    private String businessNumber;

    @Column(length = 20, nullable = false)
    private String registNumber;

    @Column(length = 20, nullable = false)
    private String region;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany
    @JoinColumn(name = "hotel_id")
    private List<Facility> facility;

}
