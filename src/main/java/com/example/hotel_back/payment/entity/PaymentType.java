package com.example.hotel_back.payment.entity;

public enum PaymentType {
    CARD("카드"),
    CASH("현금"),
    BANK_TRANSFER("계좌이체"),
    KAKAO_PAY("카카오페이"),
    NAVER_PAY("네이버페이"),
    TOSS("토스");

    private final String description;

    PaymentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
