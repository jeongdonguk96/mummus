package com.spring.mummus.image.service;

import com.spring.mummus.image.entity.Image;
import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.image.repository.ImageRepository;
import com.spring.mummus.utils.CommonUtils;
import com.spring.mummus.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Service s3Service;
    private final ImageRepository imageRepository;

    @Value("https://${cloud.aws.s3.bucket}.s3.ap-northeast-2.amazonaws.com/")
    private String S3_URL;


    // 단일 파일을 저장한다.
    @Transactional
    public String createImage(String filename, ImageDomain domain, Long domainId, Long memberId) {
        Image image = Image.from(domain, domainId, S3_URL + filename, 1, memberId);
        imageRepository.save(image);

        return image.getPath();
    }


    // 다중 파일을 저장한다.
    @Transactional
    public void createImages(List<MultipartFile> files, ImageDomain domain, Long domainId, Long memberId) throws IOException {
        List<Image> images = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            String filename = extractFilename(domain, memberId, files.get(i));
            Image image = Image.from(domain, domainId, filename, i + 1, memberId);
            images.add(image);
        }

        imageRepository.saveAll(images);
        s3Service.upload(files, domain, memberId);
    }


    // 파일명을 재조합한다.
    private String extractFilename(ImageDomain domain, Long memberId, MultipartFile files) {
        String fullCurrentMonth = DateUtils.getFullCurrentMonth();
        String originalFilename = files.getOriginalFilename();
        String randomNumber = CommonUtils.generate3RandomNumber();

        return String.format("%d/%s/%s/%s-%s", memberId, domain, fullCurrentMonth, originalFilename, randomNumber);
    }

}
