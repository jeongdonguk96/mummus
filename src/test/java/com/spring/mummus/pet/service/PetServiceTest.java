package com.spring.mummus.pet.service;

import com.spring.mummus.common.AbstractTest;
import com.spring.mummus.exception.code.PetErrorCode;
import com.spring.mummus.exception.exception.CommonException;
import com.spring.mummus.fixture.PetFixture;
import com.spring.mummus.image.entity.Image;
import com.spring.mummus.pet.dto.CreatePetRequest;
import com.spring.mummus.pet.dto.DeletePetProfileImageRequest;
import com.spring.mummus.pet.dto.GetMyPetsResponse;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.enums.Gender;
import com.spring.mummus.pet.enums.PetType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.spring.mummus.pet.enums.Gender.FEMALE;
import static com.spring.mummus.pet.enums.PetType.JINDO_DOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PetServiceTest extends AbstractTest {

    @Autowired EntityManager em;


    @Test
    @DisplayName("강아지가 정상적으로 등록된다.")
    void createPetTest() throws IOException {
        // given
        Long memberId = 1L;
        MultipartFile file = new MockMultipartFile("file", new byte[1]);
        String profileImageUrl = petService.createPetProfileImage(file, memberId);
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
    @DisplayName("내 강아지가 정상적으로 조회된다.")
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
    @DisplayName("다른 강아지가 정상적으로 조회된다.")
    void getPetTest() {
        // given
        savePet(1L, 11L, "보나", "test.com/url1");
        savePet(2L, 11L, "핑키", "test.com/url2");

        // when
        Pet pet = petService.findById(1L);

        // then
        assertThat(pet.getName()).isEqualTo("보나");
    }


    @Test
    @DisplayName("강아지의 이름이 정상적으로 변경된다.")
    void modifyPetNameTest() {
        // given
        savePet(1L, 11L, "보나", "test.com/url1");


        // when
        petService.modifyPetName(1L, "밍키");
        em.flush();
        em.clear();
        Pet pet = petRepository.findById(1L).get();

        // then
        assertThat(pet.getName()).isEqualTo("밍키");
    }


    @Test
    @DisplayName("강아지의 나이가 정상적으로 변경된다.")
    void modifyPetAgeTest() {
        // given
        savePet(1L, 11L, "보나", "test.com/url1", 1);


        // when
        petService.modifyPetAge(1L, 3);
        em.flush();
        em.clear();
        Pet pet = petRepository.findById(1L).get();

        // then
        assertThat(pet.getAge()).isEqualTo(3);
    }


    @Test
    @DisplayName("강아지의 생일이 정상적으로 변경된다.")
    void modifyPetBirthTest() {
        // given
        savePet(1L, 11L, "보나", "test.com/url1", "2023-01-01");


        // when
        petService.modifyPetBirth(1L, "2024-01-01");
        em.flush();
        em.clear();
        Pet pet = petRepository.findById(1L).get();

        // then
        assertThat(pet.getBirth()).isEqualTo("2024-01-01");
    }


    @Test
    @DisplayName("강아지의 성별이 정상적으로 변경된다.")
    void modifyPetGenderTest() {
        // given
        savePet(1L, 11L, "보나", "test.com/url1", Gender.MALE);


        // when
        petService.modifyPetGender(1L, FEMALE);
        em.flush();
        em.clear();
        Pet pet = petRepository.findById(1L).get();

        // then
        assertThat(pet.getGender()).isEqualTo(FEMALE);
    }


    @Test
    @DisplayName("강아지의 견종이 정상적으로 변경된다.")
    void modifyPetPetTypeTest() {
        // given
        savePet(1L, 11L, "보나", "test.com/url1", PetType.BEAGLE);


        // when
        petService.modifyPetType(1L, JINDO_DOG);
        em.flush();
        em.clear();
        Pet pet = petRepository.findById(1L).get();

        // then
        assertThat(pet.getPetType()).isEqualTo(JINDO_DOG);
    }


    @Test
    @DisplayName("강아지의 프로필 사진이 정상적으로 등록된다.")
    void createPetProfileImageTest() throws IOException {
        // given
        Long memberId = 1L;
        MultipartFile file = new MockMultipartFile("file", new byte[1]);

        // when
        String profileImageUrl = petService.createPetProfileImage(file, memberId);
        List<Image> images = imageRepository.findAll();

        // then
        assertThat(images).hasSize(1);
        assertThat(images.get(0).getPath()).isEqualTo(profileImageUrl);
    }


    @Test
    @DisplayName("강아지의 프로필 사진이 정상적으로 변경된다.")
    void modifyPetProfileImageTest() throws IOException {
        // given
        Long memberId = 1L;
        MultipartFile file = new MockMultipartFile("file", new byte[1]);

        String profileImageUrl = petService.createPetProfileImage(file, memberId);
        Pet pet = savePet(1L, memberId, "Bona", profileImageUrl);
        imageService.modifyDomainId(pet);

        MultipartFile newFile = new MockMultipartFile("file", new byte[1]);

        // when
        String newProfileImageUrl = petService.modifyPetProfileImage(pet.getId(), newFile, memberId);
        List<Image> images = imageRepository.findAll();
        List<Pet> pets = petRepository.findAll();

        // then
        assertThat(images).hasSize(1);
        assertThat(images.get(0).getPath()).isNotEqualTo(profileImageUrl);
        assertThat(images.get(0).getPath()).isEqualTo(newProfileImageUrl);
        assertThat(pets.get(0).getProfileImageUrl()).isEqualTo(newProfileImageUrl);
    }


    @Test
    @DisplayName("임시 등록한 강아지의 프로필 사진이 정상적으로 삭제된다.")
    void rollbackPetProfileImageTest() throws IOException {
        // given
        Long memberId = 1L;
        MultipartFile file = new MockMultipartFile("file", new byte[1]);
        String profileImageUrl = petService.createPetProfileImage(file, memberId);

        DeletePetProfileImageRequest request = new DeletePetProfileImageRequest(profileImageUrl);

        // when
        petService.rollbackPetProfileImage(request);
        List<Image> images = imageRepository.findAll();

        // then
        assertThat(images).hasSize(0);
    }


    @Test
    @DisplayName("강아지의 프로필 사진이 정상적으로 삭제된다.")
    void deletePetProfileImageTest() throws IOException {
        // given
        Long memberId = 1L;
        MultipartFile file = new MockMultipartFile("file", new byte[1]);
        String profileImageUrl = petService.createPetProfileImage(file, memberId);
        CreatePetRequest createPetRequest = new CreatePetRequest("보나", 1, "2020-01-01", FEMALE, JINDO_DOG, profileImageUrl);
        Pet pet = petService.createPet(createPetRequest, memberId);

        DeletePetProfileImageRequest request = new DeletePetProfileImageRequest(profileImageUrl);

        // when
        petService.deletePetProfileImage(pet.getId(), request);
        List<Image> images = imageRepository.findAll();

        // then
        assertThat(images).hasSize(0);
        assertThat(pet.getProfileImageUrl()).isNull();
    }


    @Test
    @DisplayName("강아지가 정상적으로 삭제된다.")
    void deletePetTest() {
        // given
        Pet pet = savePet(1L, 1L, "보나", "sd");

        // when
        petService.deletePet(pet.getId());
        List<Pet> pets = petRepository.findAll();

        // then
        assertThat(pets).isEmpty();
    }


    @Test
    @DisplayName("중복된 강아지를 체크한다.")
    void checkDuplicatedPetTest() throws IOException {
        //given
        Long memberId = 1L;
        MultipartFile file = new MockMultipartFile("file", new byte[1]);
        String profileImageUrl = petService.createPetProfileImage(file, memberId);
        CreatePetRequest request1 = new CreatePetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG, profileImageUrl);
        CreatePetRequest request2 = new CreatePetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG, profileImageUrl);
        petService.createPetProfileImage(file, memberId);

        //when
        petService.createPet(request1, memberId);
        CommonException petException = Assertions.assertThrows(CommonException.class, () -> petService.createPet(request2, memberId));


        //then
        assertEquals(PetErrorCode.DUPLICATED_PET, petException.getErrorCode());
    }


    private Pet savePet(Long id, Long memberId, String name, String profileImageUrl) {
        Pet pet = PetFixture.createPet(id, memberId, name, profileImageUrl);
        return petRepository.save(pet);
    }


    private Pet savePet(Long id, Long memberId, String name, String profileImageUrl, int age) {
        Pet pet = PetFixture.createPet(id, memberId, name, profileImageUrl, age);
        return petRepository.save(pet);
    }


    private Pet savePet(Long id, Long memberId, String name, String profileImageUrl, String birth) {
        Pet pet = PetFixture.createPet(id, memberId, name, profileImageUrl, birth);
        return petRepository.save(pet);
    }


    private Pet savePet(Long id, Long memberId, String name, String profileImageUrl, Gender gender) {
        Pet pet = PetFixture.createPet(id, memberId, name, profileImageUrl, gender);
        return petRepository.save(pet);
    }


    private Pet savePet(Long id, Long memberId, String name, String profileImageUrl, PetType petType) {
        Pet pet = PetFixture.createPet(id, memberId, name, profileImageUrl, petType);
        return petRepository.save(pet);
    }

}