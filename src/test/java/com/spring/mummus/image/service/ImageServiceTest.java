package com.spring.mummus.image.service;

import com.spring.mummus.common.AbstractTest;
import com.spring.mummus.image.entity.Image;
import com.spring.mummus.image.enums.ImageDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ImageServiceTest extends AbstractTest {

    @Value("https://${cloud.aws.s3.bucket}.s3.ap-northeast-2.amazonaws.com/")
    String S3_URL;

    @Test
    @DisplayName("단일 파일이 정상적으로 저장된다.")
    void createImageTest() {
        // given
        String filename = "1/PET/202410/pic.png";
        ImageDomain domain = ImageDomain.PET;
        Long domainId = 1L;
        Long memberId = 1L;

        // when
        String imagePath = imageService.createImage(filename, domain, domainId, memberId);
        List<Image> images = imageRepository.findAll();

        // then
        assertThat(images).hasSize(1);
        assertThat(imagePath).isEqualTo(S3_URL + filename);
        assertThat(images.get(0).getPath()).isEqualTo(imagePath);
    }

}