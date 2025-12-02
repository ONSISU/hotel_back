package com.example.hotel_back.ownhotel.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.hotel_back.hotel.entity.Hotel;

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
public class OwnHotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ownHotelId;

	@Column(columnDefinition = "BIGINT UNSIGNED")
	private Long price;

	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private int countRoom;

	@Column(columnDefinition = "TIME")
	@Builder.Default
	private LocalTime checkInTime = LocalTime.of(15, 0);

	@Column(columnDefinition = "TIME")
	@Builder.Default
	private LocalTime checkOutTime = LocalTime.of(11, 0);

	@Column(length = 50)
	private String roomType;

	@Column(length = 100)
	private String roomName;

	@Column(columnDefinition = "INT UNSIGNED")
	@Builder.Default
	private int maxPerson = 2;

	@Column(columnDefinition = "INT UNSIGNED")
	@Builder.Default
	private int minPerson = 1;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	@Builder.Default
	private Double owner_discount = 5.0;
	@Builder.Default
	private Double platform_discount = 10.0;

}
