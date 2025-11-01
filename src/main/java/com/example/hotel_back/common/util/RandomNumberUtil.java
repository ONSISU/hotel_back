package com.example.hotel_back.common.util;

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

    public static int get랜덤인증번호() {
        int 랜덤인증번호 = ThreadLocalRandom.current().nextInt(1000, 9999);
        return 랜덤인증번호;
    }

}
