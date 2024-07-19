package com.spring.mummus.oauth2.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuth2 {
    KAKAO("KAKAO"),

    ;

    private final String value;
}
