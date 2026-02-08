package com.example.hotel_back.payment.mapper;

import com.example.hotel_back.payment.dto.PaymentAvailableHotelRequest;
import com.example.hotel_back.payment.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentMapper {
	int checkAvailablePayHotel(PaymentAvailableHotelRequest request);

	List<Payment> getPaymentList(@Param("email") String email);
}
