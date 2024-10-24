package com.spring.mummus.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.pet.entity.Pet;
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
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET;


    // 단일 파일을 S3에 업로드한다.
    public String upload(MultipartFile file, ImageDomain domain, Long memberId) throws IOException {
        String fileName = extractFilename(domain, memberId, file);
        ObjectMetadata metadata = setObjectMetadata(file);
        amazonS3Client.putObject(BUCKET, fileName, file.getInputStream(), metadata);

        return fileName;
    }


    // S3에 있는 단일 파일을 삭제한다.
    public void delete(String fileName) {
        amazonS3Client.deleteObject(BUCKET, fileName);
    }


    // 다중 파일을 S3에 업로드한다.
    public void upload(List<MultipartFile> files, ImageDomain domain, Long memberId) throws IOException {
        for (MultipartFile file : files) {
            String filename = extractFilename(domain, memberId, file);
            ObjectMetadata metadata = setObjectMetadata(file);

            amazonS3Client.putObject(BUCKET, filename, file.getInputStream(), metadata);
        }
    }


    // S3에서 파일을 가져온다.
    @Transactional
    public List<String> getFiles(List<Pet> pets) {
        List<String> imageUrls = new ArrayList<>();

        for (Pet pet : pets) {
            String imageUrl = String.valueOf(amazonS3Client.getObject(BUCKET, pet.getProfileImageUrl()));
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }


    // 파일명을 재조합한다.
    private String extractFilename(ImageDomain domain, Long memberId, MultipartFile file) {
        String fullCurrentMonth = DateUtils.getFullCurrentMonth();
        String originalFilename = file.getOriginalFilename();
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
