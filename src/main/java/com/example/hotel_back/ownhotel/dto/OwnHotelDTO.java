package com.example.hotel_back.ownhotel.dto;

import com.example.hotel_back.ownhotel.entity.OwnHotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnHotelDTO {

	private Long ownHotelId;

	private Long price;

	private int countRoom;

	private LocalTime checkInTime;
	private LocalTime checkOutTime;

	private String roomType;
	private String roomName;

	private int maxPerson;
	private int minPerson;

	private List<String> pictureList;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public OwnHotelDTO(OwnHotel ownHotel) {
		this.ownHotelId = ownHotel.getOwnHotelId();
		this.price = ownHotel.getPrice();
		this.countRoom = ownHotel.getCountRoom();
		this.checkInTime = ownHotel.getCheckInTime();
		this.checkOutTime = ownHotel.getCheckOutTime();
		this.roomType = ownHotel.getRoomType();
		this.roomName = ownHotel.getRoomName();
		this.maxPerson = ownHotel.getMaxPerson();
		this.minPerson = ownHotel.getMinPerson();
		this.createdAt = ownHotel.getCreatedAt();
		this.updatedAt = ownHotel.getUpdatedAt();
	}


}
