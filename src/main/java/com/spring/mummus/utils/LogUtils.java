package com.spring.mummus.utils;

public class LogUtils {

    // 고유 TrxNo를 생성한다.
    public static String generateTrxNo() {
        long millis = System.currentTimeMillis();
        String randomNumber = CommonUtils.generate3RandomNumber();

        return millis + randomNumber;
    }

}
