package com.example.hotel_back.payment.repository;

import com.example.hotel_back.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
