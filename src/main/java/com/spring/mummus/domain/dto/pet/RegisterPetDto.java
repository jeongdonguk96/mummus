package com.spring.mummus.domain.dto.pet;

import com.spring.mummus.domain.type.Gender;
import com.spring.mummus.domain.type.PetType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterPetDto {
    private String name;
    private Integer age;
    private Gender gender;
    private PetType petType;
    private Long memberId;
}
