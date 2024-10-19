package com.spring.mummus.pet.dto;

import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.enums.Gender;
import com.spring.mummus.pet.enums.PetType;

public record CreatePetRequest(
        String name,
        int age,
        String birth,
        Gender gender,
        PetType petType
) {

    public Pet from(Long memberId) {
        return Pet.builder()
                .name(this.name)
                .age(this.age)
                .birth(this.birth)
                .gender(this.gender)
                .petType(this.petType)
                .memberId(memberId)
                .build();
    }
}
