package com.spring.mummus.image.service;

import com.spring.mummus.common.AbstractTest;
import com.spring.mummus.fixture.PetFixture;
import com.spring.mummus.pet.dto.DeletePetProfileImageRequest;
import com.spring.mummus.image.entity.Image;
import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.pet.dto.CreatePetRequest;
import com.spring.mummus.pet.entity.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.spring.mummus.pet.enums.Gender.FEMALE;
import static com.spring.mummus.pet.enums.PetType.JINDO_DOG;
import static org.assertj.core.api.Assertions.assertThat;

class ImageServiceTest extends AbstractTest {

    @Value("https://${cloud.aws.s3.bucket}.s3.ap-northeast-2.amazonaws.com/")
    String S3_URL;

    @Test
    @DisplayName("단일 이미지가 정상적으로 저장된다.")
    void createImageTest() throws IOException {
        // given
        Long memberId = 1L;
        MultipartFile file = new MockMultipartFile("file", new byte[1]);
        ImageDomain domain = ImageDomain.PET;

        // when
        String fileName = imageService.createImage(file, domain, memberId);
        List<Image> images = imageRepository.findAll();

        // then
        assertThat(images).hasSize(1);
        assertThat(images.get(0).getPath()).isEqualTo(fileName);
    }


    @Test
    @DisplayName("단일 이미지가 정상적으로 수정된다.")
    void modifyImageTest() throws IOException {
        // given
        Long memberId = 1L;
        MultipartFile file = new MockMultipartFile("file", new byte[1]);
        ImageDomain domain = ImageDomain.PET;
        String fileName = imageService.createImage(file, domain, memberId);
        Pet pet = savePet(1L, memberId, "Bona", fileName);
        imageService.modifyDomainId(pet);

        MultipartFile newFile = new MockMultipartFile("file", new byte[1]);

        // when
        String newFileName = imageService.modifyProfileImage(newFile, ImageDomain.PET, pet.getId(), memberId);
        List<Image> images = imageRepository.findAll();
        List<Pet> pets = petRepository.findAll();

        // then
        assertThat(images).hasSize(1);
        assertThat(images.get(0).getPath()).isNotEqualTo(fileName);
        assertThat(images.get(0).getPath()).isEqualTo(newFileName);
        assertThat(pets.get(0).getProfileImageUrl()).isEqualTo(newFileName);
    }


    @Test
    @DisplayName("단일 이미지가 정상적으로 삭제된다.")
    void deleteImageTest() throws IOException {
        // given
        Long memberId = 1L;
        MultipartFile file = new MockMultipartFile("file", new byte[1]);
        ImageDomain domain = ImageDomain.PET;
        String fileName = imageService.createImage(file, domain, memberId);
        DeletePetProfileImageRequest request = new DeletePetProfileImageRequest(fileName);
        Pet pet = savePet(1L, memberId, "Bona", fileName);
        imageService.modifyDomainId(pet);

        // when
        List<Image> images1 = imageRepository.findAll();
        imageService.deleteImage(request, pet.getId());
        List<Image> images2 = imageRepository.findAll();

        // then
        assertThat(images1).hasSize(1);
        assertThat(images2).isEmpty();
    }


    @Test
    @DisplayName("이미지 데이터의 domainId가 정상적으로 수정된다.")
    void modifyDomainIdTest() throws IOException {
        // given
        Long memberId = 1L;
        MultipartFile file = new MockMultipartFile("file", new byte[1]);
        ImageDomain domain = ImageDomain.PET;
        String imagePath = imageService.createImage(file, domain, memberId);

        CreatePetRequest request = new CreatePetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG, imagePath);
        Pet pet = petService.createPet(request, memberId);
        Image image = imageRepository.getImage(imagePath);

        // when
        imageService.modifyDomainId(pet);

        // then
        assertThat(image.getDomainId()).isEqualTo(pet.getId());
    }


    private Pet savePet(Long id, Long memberId, String name, String profileImageUrl) {
        Pet pet = PetFixture.createPet(id, memberId, name, profileImageUrl);
        return petRepository.save(pet);
    }

}