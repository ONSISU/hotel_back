package com.example.hotel_back.hotel.entity;

import com.example.hotel_back.ownhotel.entity.OwnHotel;
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
public class HotelPicture {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hotelPictureId;

	@Column(length = 100, nullable = false, unique = true)
	private String picutreName;

	@Column(length = 200)
	private String pictureUrl;

	@CreatedDate
	@Column(columnDefinition = "TIMESTAMP", updatable = false)
	private LocalDateTime createdAt;

	@Column(columnDefinition = "TIMESTAMP")
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;
}
