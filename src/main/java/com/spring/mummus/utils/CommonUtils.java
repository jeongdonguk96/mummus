package com.spring.mummus.utils;

import java.util.Random;

public class CommonUtils {

    // 3자리의 랜덤 숫자를 생성한다.
    public static String generate3RandomNumber() {
        Random random = new Random();
        return String.valueOf(random.nextInt(900) + 100);
    }

}
