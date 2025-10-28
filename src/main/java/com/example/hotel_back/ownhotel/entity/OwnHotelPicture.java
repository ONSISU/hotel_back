package com.example.hotel_back.ownhotel.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class OwnHotelPicture {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ownHotelPictureId;

  @Column(length = 100, nullable = false, unique = true)
  private String picutreName;

  @Column(length = 200)
  private String picutreUrl;

  @CreatedDate
  @Column(columnDefinition = "TIMESTAMP", updatable = false)
  private LocalDateTime createdAt;

  @Column(columnDefinition = "TIMESTAMP")
  @LastModifiedDate
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "own_hotel_id")
  private OwnHotel ownHotel;
  
}
