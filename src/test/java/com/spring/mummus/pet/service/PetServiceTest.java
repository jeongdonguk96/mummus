package com.spring.mummus.pet.service;

import com.spring.mummus.common.AbstractTest;
import com.spring.mummus.pet.dto.RegisterPetRequest;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.spring.mummus.pet.enums.Gender.FEMALE;
import static com.spring.mummus.pet.enums.PetType.JINDO_DOG;
import static org.assertj.core.api.Assertions.assertThat;

class PetServiceTest extends AbstractTest {

    @Autowired PetService petService;
    @Autowired PetRepository petRepository;


    @Test
    @DisplayName("동물이 정상적으로 등록된다")
    void registerPetTest() {
        // given
        RegisterPetRequest request = new RegisterPetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG, 1L);

        // when
        Pet pet = petService.registerPet(request);

        // then
        assertThat(pet.getName()).isEqualTo("bona");
        assertThat(pet.getAge()).isEqualTo(4);
        assertThat(pet.getMemberId()).isEqualTo(1L);
        assertThat(pet.getId()).isNotNull();
    }

}