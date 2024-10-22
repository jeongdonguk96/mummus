package com.spring.mummus.image.service;

import com.spring.mummus.common.AbstractTest;
import com.spring.mummus.image.entity.Image;
import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.pet.dto.CreatePetRequest;
import com.spring.mummus.pet.entity.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static com.spring.mummus.pet.enums.Gender.FEMALE;
import static com.spring.mummus.pet.enums.PetType.JINDO_DOG;
import static org.assertj.core.api.Assertions.assertThat;

class ImageServiceTest extends AbstractTest {

    @Value("https://${cloud.aws.s3.bucket}.s3.ap-northeast-2.amazonaws.com/")
    String S3_URL;

    @Test
    @DisplayName("단일 파일이 정상적으로 저장된다.")
    void createImageTest() {
        // given
        Long memberId = 1L;
        String filename = "1/PET/202410/pic.png";
        ImageDomain domain = ImageDomain.PET;

        // when
        String imagePath = imageService.createImage(filename, domain, memberId);
        List<Image> images = imageRepository.findAll();

        // then
        assertThat(images).hasSize(1);
        assertThat(imagePath).isEqualTo(S3_URL + filename);
        assertThat(images.get(0).getPath()).isEqualTo(imagePath);
    }


    @Test
    @DisplayName("이미지 데이터의 domainId가 정상적으로 수정된다.")
    void modifyDomainIdTest() {
        // given
        Long memberId = 1L;
        String filename = "1/PET/202410/pic.png";
        ImageDomain domain = ImageDomain.PET;
        String imagePath = imageService.createImage(filename, domain, memberId);

        CreatePetRequest request = new CreatePetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG, imagePath);
        Pet pet = petService.createPet(request, memberId);
        Image image = imageRepository.getImage(imagePath);

        // when
        imageService.modifyDomainId(pet);

        // then
        assertThat(image.getDomainId()).isEqualTo(pet.getId());
    }

}