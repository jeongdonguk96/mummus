package com.spring.mummus.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    // 고유 TrxNo를 생성한다.
    public static String getFullCurrentMonth() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        return today.format(formatter);
    }

}
