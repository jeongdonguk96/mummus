package com.spring.mummus.pet.dto;

import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.enums.Gender;
import com.spring.mummus.pet.enums.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPetRequest {
    private String name;
    private int age;
    private String birth;
    private Gender gender;
    private PetType petType;

    public Pet toEntity(Long memberId) {
        return Pet.builder()
                .name(this.getName())
                .age(this.getAge())
                .birth(this.getBirth())
                .gender(this.getGender())
                .petType(this.getPetType())
                .memberId(memberId)
                .build();
    }
}
