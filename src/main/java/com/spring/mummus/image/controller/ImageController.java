package com.spring.mummus.image.controller;

import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.image.service.ImageService;
import com.spring.mummus.image.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final S3Service s3Service;
    private final ImageService imageService;


    // S3에 이미지 파일을 업로드하고 DB에 이미지 데이터를 생성한다.
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createImage(@RequestPart(name = "file") MultipartFile file) throws IOException {
        Long memberId = 1L;

        String filename = s3Service.upload(file, ImageDomain.PET, memberId);
        return imageService.createImage(filename, ImageDomain.PET, memberId);
    }

}
