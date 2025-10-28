package com.example.hotel_back.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberUtil {

    public static String get랜덤사업자번호() {
        long number = ThreadLocalRandom.current()
                .nextLong(1000000000L, 10000000000L); // 10-digit range
        return String.valueOf(number);
    }

    public static String get판매업자신고번호() {
        long number = ThreadLocalRandom.current()
                .nextLong(100000000000000L, 1000000000000000L); // 15-digit range
        return String.valueOf(number);
    }

}
