package com.spring.mummus.pet.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    BOY("BOY"), GIRL("GIRL");

    private final String gender;
}
