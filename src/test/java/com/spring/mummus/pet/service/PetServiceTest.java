package com.spring.mummus.pet.service;

import com.spring.mummus.common.AbstractTest;
import com.spring.mummus.exception.enums.PetErrorCode;
import com.spring.mummus.exception.exception.PetException;
import com.spring.mummus.fixture.PetFixture;
import com.spring.mummus.pet.dto.RegisterPetRequest;
import com.spring.mummus.pet.entity.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.spring.mummus.pet.enums.Gender.FEMALE;
import static com.spring.mummus.pet.enums.PetType.JINDO_DOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PetServiceTest extends AbstractTest {

    @Test
    @DisplayName("강아지가 정상적으로 등록된다.")
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


    @Test
    @DisplayName("강아지의 팔로워 수가 정상적으로 증가한다.")
    void increaseFollowerCountTest() {
        // given
        Pet pet = savePet(1L, 1L);

        // when
        petService.increaseFollowerCount(pet);

        // then
        assertThat(pet.getFollowerCount()).isEqualTo(1);
    }


    @Test
    @DisplayName("강아지 등록 시 중복체크를 진행한다.")
    void checkDuplicatedPetTest() {
        // given
        RegisterPetRequest request1 = new RegisterPetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG, 1L);
        RegisterPetRequest request2 = new RegisterPetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG, 1L);

        // when
        petService.registerPet(request1);
        PetException petException = assertThrows(PetException.class, () -> petService.registerPet(request2));

        // then
        assertEquals(PetErrorCode.DUPLICATED_PET, petException.getErrorCode());
    }


    @Test
    @DisplayName("강아지 존재 여부가 확인된다.")
    void findByIdTest() {
        //given
        RegisterPetRequest request = new RegisterPetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG, 1L);

        //when
        petService.registerPet(request);
        Pet pet = petRepository.findById(1L).get();
        Pet targetPet = petService.findById(1L);


        //then
        assertThat(pet.getId()).isEqualTo(targetPet.getId());
        assertThat(pet.getName()).isEqualTo(targetPet.getName());
        assertThat(pet.getAge()).isEqualTo(targetPet.getAge());
    }


    private Pet savePet(Long id, Long memberId) {
        Pet pet = PetFixture.createPet(id, memberId);
        return petRepository.save(pet);
    }

}