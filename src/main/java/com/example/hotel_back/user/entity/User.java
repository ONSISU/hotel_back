package com.example.hotel_back.user.entity;

import com.example.hotel_back.user.enums.Role;
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
@EntityListeners(AuditingEntityListener.class) // EnableJpaAuditing과 함께 사용해야함. @CreatedDate를 사용하기 위해 추가
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long userId;

   @Column(columnDefinition = "INT")
   private int oauthId;

   @Column(length = 20, nullable = false, unique = true)
   private String email;

   @Column(length = 60, nullable = false)
   private String password;

   @Column(length = 20, nullable = false)
   private String fullName;

   @Column(length = 20)
   private String phone;

   @CreatedDate
   @Column(updatable = false)
   private LocalDateTime createdAt;

   @LastModifiedDate
   private LocalDateTime updatedAt;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "profile_photo_id", unique = true)
   private ProfilePhoto profilePhoto;

   @Enumerated(EnumType.STRING)
   @Builder.Default
   private Role role = Role.USER;

}
