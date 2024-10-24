package com.spring.mummus.pet.service;

import com.spring.mummus.common.AbstractTest;
import com.spring.mummus.exception.enums.PetErrorCode;
import com.spring.mummus.exception.exception.PetException;
import com.spring.mummus.fixture.PetFixture;
import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.pet.dto.CreatePetRequest;
import com.spring.mummus.pet.dto.GetMyPetsResponse;
import com.spring.mummus.pet.entity.Pet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.spring.mummus.pet.enums.Gender.FEMALE;
import static com.spring.mummus.pet.enums.PetType.JINDO_DOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PetServiceTest extends AbstractTest {

    @Test
    @DisplayName("강아지가 정상적으로 등록된다.")
    void createPetTest() throws IOException {
        // given
        Long memberId = 1L;
        MultipartFile file = new MockMultipartFile("file", new byte[1]);
        String profileImageUrl = imageService.createImage(file, ImageDomain.PET, memberId);
        CreatePetRequest request = new CreatePetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG, profileImageUrl);

        // when
        Pet pet = petService.createPet(request, memberId);

        // then
        assertThat(pet.getName()).isEqualTo("bona");
        assertThat(pet.getAge()).isEqualTo(4);
        assertThat(pet.getMemberId()).isEqualTo(1L);
        assertThat(pet.getId()).isNotNull();
    }


    @Test
    @DisplayName("강아지가 정상적으로 조회된다.")
    void getMyPetsTest() {
        // given
        savePet(1L, 11L, "보나", "test.com/url1");
        savePet(2L, 11L, "핑키", "test.com/url2");

        // when
        List<GetMyPetsResponse> myPets = petService.getMyPets(11L);

        // then
        assertThat(myPets.get(0).name()).isEqualTo("보나");
        assertThat(myPets.get(0).profileImageUrl()).isEqualTo("test.com/url1");

        assertThat(myPets.get(1).name()).isEqualTo("핑키");
        assertThat(myPets.get(1).profileImageUrl()).isEqualTo("test.com/url2");
    }



    @Test
    @DisplayName("강아지 존재 여부가 확인된다.")
    void findByIdTest() throws IOException {
        //given
        Long memberId = 1L;
        MultipartFile file = new MockMultipartFile("file", new byte[1]);
        String profileImageUrl = imageService.createImage(file, ImageDomain.PET, memberId);
        CreatePetRequest request = new CreatePetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG, profileImageUrl);
        imageService.createImage(file, ImageDomain.PET, memberId);

        //when
        petService.createPet(request, memberId);
        Pet pet = petRepository.findById(1L).get();
        Pet targetPet = petService.findById(1L);


        //then
        assertThat(pet.getId()).isEqualTo(targetPet.getId());
        assertThat(pet.getName()).isEqualTo(targetPet.getName());
        assertThat(pet.getAge()).isEqualTo(targetPet.getAge());
    }


    @Test
    @DisplayName("중복된 강아지를 체크한다.")
    void checkDuplicatedPetTest() throws IOException {
        //given
        Long memberId = 1L;
        MultipartFile file = new MockMultipartFile("file", new byte[1]);
        String profileImageUrl = imageService.createImage(file, ImageDomain.PET, memberId);
        CreatePetRequest request1 = new CreatePetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG, profileImageUrl);
        CreatePetRequest request2 = new CreatePetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG, profileImageUrl);
        imageService.createImage(file, ImageDomain.PET, memberId);

        //when
        petService.createPet(request1, memberId);
        PetException petException = Assertions.assertThrows(PetException.class, () -> petService.createPet(request2, memberId));


        //then
        assertEquals(PetErrorCode.DUPLICATED_PET, petException.getErrorCode());
    }


    private Pet savePet(Long id, Long memberId, String name, String profileImageUrl) {
        Pet pet = PetFixture.createPet(id, memberId, name, profileImageUrl);
        return petRepository.save(pet);
    }

}