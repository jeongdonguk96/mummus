package com.spring.mummus.image.controller;

import com.spring.mummus.image.dto.DeleteImageRequest;
import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;


    // S3에 이미지 파일을 업로드하고 DB에 이미지 데이터를 생성한다.
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createImage(@RequestPart(name = "file") MultipartFile file) throws IOException {
        Long memberId = 1L;
        return imageService.createImage(file, ImageDomain.PET, memberId);
    }


    // S3에 기존 이미지 파일을 삭제하고 새로운 파일을 업로드하고 DB에도 기존 이미지 파일을 삭제하고 새로운 이미지 데이터를 생성한다.
    @PostMapping(value = "/{petId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void modifyImage(
            @RequestPart(name = "file") MultipartFile file,
            @PathVariable Long petId
    ) throws IOException {
        Long memberId = 1L;
        imageService.modifyImage(file, ImageDomain.PET, petId, memberId);
    }


    // S3에 있는 이미지 파일을 삭제하고 DB에서 이미지 데이터를 삭제한다.
    @DeleteMapping(value = "/{petId}")
    public void deleteImage(
            @RequestBody DeleteImageRequest request,
            @PathVariable Long petId
    ) {
        imageService.deleteImage(request, petId);
    }

}
