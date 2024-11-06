package com.spring.mummus.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponse {
    SUCCESS(0),
    FAIL(-1);

    private final int code;
}
