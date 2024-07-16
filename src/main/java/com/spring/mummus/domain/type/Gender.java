package com.spring.mummus.domain.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Gender {
    BOY("BOY"), GIRL("GIRL");

    private final String gender;
}
