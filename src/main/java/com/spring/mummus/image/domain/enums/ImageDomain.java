package com.spring.mummus.image.domain.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageDomain {
    PET("PET"),
    ;

    private final String value;
}
