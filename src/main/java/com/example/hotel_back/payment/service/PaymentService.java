package com.example.hotel_back.payment.service;

import com.example.hotel_back.payment.dto.PaymentAvailableHotelRequest;
import com.example.hotel_back.payment.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
	private final PaymentMapper paymentMapper;

	public int checkAvailablePayHotel(PaymentAvailableHotelRequest request) {
		return paymentMapper.checkAvailablePayHotel(request);
	}

}
