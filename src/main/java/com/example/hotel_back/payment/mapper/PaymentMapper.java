package com.example.hotel_back.payment.mapper;

import com.example.hotel_back.payment.dto.PaymentAvailableHotelRequest;

public interface PaymentMapper {
	int checkAvailablePayHotel(PaymentAvailableHotelRequest request);
}
