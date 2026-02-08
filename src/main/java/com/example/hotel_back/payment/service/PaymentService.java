package com.example.hotel_back.payment.service;

import com.example.hotel_back.payment.dto.PaymentAvailableHotelRequest;
import com.example.hotel_back.payment.entity.Payment;
import com.example.hotel_back.payment.mapper.PaymentMapper;
import com.example.hotel_back.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
	private final PaymentMapper paymentMapper;
	private final PaymentRepository paymentRepository;

	public int checkAvailablePayHotel(PaymentAvailableHotelRequest request) {
		return paymentMapper.checkAvailablePayHotel(request);
	}

	public Payment getPaymentDetail(Long id) {
		return paymentRepository.findById(id).orElseThrow();
	}

	public List<Payment> getPaymentList(String email) {
		return paymentMapper.getPaymentList(email);
	}
}
