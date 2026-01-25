package com.example.hotel_back.payment.mapper;

import com.example.hotel_back.payment.dto.PaymentAvailableHotelRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
	int checkAvailablePayHotel(PaymentAvailableHotelRequest request);
}
