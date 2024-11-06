package com.spring.mummus.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponse {
    SUCCESS(0, "SUCCESS"),
    FAIL(-1, "FAIL");

    private final int code;
    private final String message;
}
