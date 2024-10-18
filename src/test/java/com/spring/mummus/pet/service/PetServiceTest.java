package com.spring.mummus.pet.service;

import com.spring.mummus.common.AbstractTest;
import com.spring.mummus.pet.dto.RegisterPetRequest;
import com.spring.mummus.pet.entity.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.spring.mummus.pet.enums.Gender.FEMALE;
import static com.spring.mummus.pet.enums.PetType.JINDO_DOG;
import static org.assertj.core.api.Assertions.assertThat;

class PetServiceTest extends AbstractTest {

    @Test
    @DisplayName("강아지가 정상적으로 등록된다.")
    void createPetTest() throws IOException {
        // given
        RegisterPetRequest request = new RegisterPetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG);
        MultipartFile file = new MockMultipartFile("file", new byte[1]);
        Long memberId = 1L;

        // when
        Pet pet = petService.createPet(request, file, memberId);

        // then
        assertThat(pet.getName()).isEqualTo("bona");
        assertThat(pet.getAge()).isEqualTo(4);
        assertThat(pet.getMemberId()).isEqualTo(1L);
        assertThat(pet.getId()).isNotNull();
    }



    @Test
    @DisplayName("강아지 존재 여부가 확인된다.")
    void findByIdTest() throws IOException {
        //given
        RegisterPetRequest request = new RegisterPetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG);
        MultipartFile file = new MockMultipartFile("file", new byte[1]);
        Long memberId = 1L;

        //when
        petService.createPet(request, file, memberId);
        Pet pet = petRepository.findById(1L).get();
        Pet targetPet = petService.findById(1L);


        //then
        assertThat(pet.getId()).isEqualTo(targetPet.getId());
        assertThat(pet.getName()).isEqualTo(targetPet.getName());
        assertThat(pet.getAge()).isEqualTo(targetPet.getAge());
    }


}