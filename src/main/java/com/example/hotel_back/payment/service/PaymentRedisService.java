package com.example.hotel_back.payment.service;

import com.example.hotel_back.payment.dto.PaymentTemp;
import com.example.hotel_back.reservation.dto.ReserveRoomRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PaymentRedisService {

	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

	private static final long TTL_MINUTES = 10;

	public void save(PaymentTemp temp) {
		String key = key(temp);

		redisTemplate.opsForValue().set(
						key,
						write(temp),
						TTL_MINUTES,
						TimeUnit.MINUTES
		);
	}

	public PaymentTemp get(PaymentTemp request) {
		String json = redisTemplate.opsForValue().get(key(request));
		return json == null ? null : read(json);
	}

	public void delete(PaymentTemp request) {
		redisTemplate.delete(key(request));
	}

	private String key(PaymentTemp request) {
		return "payment:pending:" + request.getOwnHotelId() + request.getUserId() + request.getStartDate();
	}

	private String write(PaymentTemp temp) {
		try {
			return objectMapper.writeValueAsString(temp);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private PaymentTemp read(String json) {
		try {
			return objectMapper.readValue(json, PaymentTemp.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}