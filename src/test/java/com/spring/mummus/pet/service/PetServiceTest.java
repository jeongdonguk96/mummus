package com.spring.mummus.pet.service;

import com.spring.mummus.pet.domain.dto.RegisterPetRequest;
import com.spring.mummus.pet.domain.entity.Pet;
import com.spring.mummus.pet.domain.type.Gender;
import com.spring.mummus.pet.domain.type.PetType;
import com.spring.mummus.pet.repository.PetRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PetServiceTest {

    @Autowired PetService petService;
    @Autowired PetRepository petRepository;


    @AfterEach
    void tearDown() {
        petRepository.deleteAllInBatch();
    }


    @Test
    @DisplayName("동물 등록이 정상적으로 성공한다")
    void registerPetTest() {
        // given
        RegisterPetRequest registerPetRequest = new RegisterPetRequest("bona", 5, Gender.GIRL, PetType.JINDO_DOG, 1L);

        // when
        Pet pet = petService.registerPet(registerPetRequest);

        // then
        assertThat(pet.getName()).isEqualTo("bona");
        assertThat(pet.getAge()).isEqualTo(5);
        assertThat(pet.getMemberId()).isEqualTo(1L);
        assertThat(pet.getId()).isNotNull();
    }

}