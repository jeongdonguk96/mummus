package com.spring.mummus.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
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

    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET;


    // 파일을 S3에 업로드한다.
    @Transactional
    public void upload(MultipartFile[] files, ImageDomain domain, Long memberId) throws IOException {
        for (MultipartFile file : files) {
            String filename = extractFilename(domain, memberId, file);
            ObjectMetadata metadata = setObjectMetadata(file);

            amazonS3Client.putObject(BUCKET, filename, file.getInputStream(), metadata);
        }
    }


    // 파일을 저장한다.
    @Transactional
    public void insert(MultipartFile[] files, ImageDomain domain, Long memberId) {
        List<Image> images = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            String filename = extractFilename(domain, memberId, files[i]);
            Image image = Image.from(domain, filename, i + 1, memberId);
            images.add(image);
        }

        imageRepository.saveAll(images);
    }


    // 파일명을 재조합한다.
    private String extractFilename(ImageDomain domain, Long memberId, MultipartFile files) {
        String fullCurrentMonth = DateUtils.getFullCurrentMonth();
        String originalFilename = files.getOriginalFilename();
        String randomNumber = CommonUtils.generate3RandomNumber();

        return String.format("%d/%s/%s/%s-%s", memberId, domain, fullCurrentMonth, originalFilename, randomNumber);
    }


    // Metadata 정보를 설정한다.
    private ObjectMetadata setObjectMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        return metadata;
    }

}
