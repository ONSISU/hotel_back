package com.example.hotel_back.notice.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.hotel_back.ownhotel.entity.OwnHotel;

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
public class Notice {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long noticeId;

  @Column(length = 100, nullable = false)
  private String content;
  
  @Column(length = 100, nullable = false)
  private String usageGuide;
  
  @Column(length = 100, nullable = false)
  private String introduction;

  @ManyToOne
  @JoinColumn(name = "own_hotel_id")
  private OwnHotel ownHotel;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;
  
  @LastModifiedDate
  private LocalDateTime updatedAt;
  
}
