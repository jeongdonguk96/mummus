package com.spring.mummus.image.controller;

import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 파일을 업로드한다.
    @PostMapping
    public void uploadTest(
            @RequestParam(name = "files") List<MultipartFile> multipartFiles,
            @RequestParam(name = "domain") ImageDomain domain
    ) throws IOException {
        Long domainId = 1L;
        Long memberId = 2L;
        imageService.upload(multipartFiles, domain, memberId);
        imageService.insert(multipartFiles, domain, domainId, memberId);
    }

}
