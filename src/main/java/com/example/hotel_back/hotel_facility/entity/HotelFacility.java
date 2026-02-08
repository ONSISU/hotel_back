package com.example.hotel_back.hotel_facility.entity;

import com.example.hotel_back.facility.entity.Facility;
import com.example.hotel_back.hotel.entity.Hotel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelFacility {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hfid;

	@ManyToOne
	private Hotel hotel;

	@ManyToOne
	private Facility facility;

	private boolean isFree;

	private int price;

}
