package com.spring.mummus.image.service;

import com.spring.mummus.exception.enums.PetErrorCode;
import com.spring.mummus.exception.exception.PetException;
import com.spring.mummus.image.dto.DeleteImageRequest;
import com.spring.mummus.image.entity.Image;
import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.image.repository.ImageRepository;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.repository.PetRepository;
import com.spring.mummus.utils.CommonUtils;
import com.spring.mummus.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Service s3Service;
    private final PetRepository petRepository;
    private final ImageRepository imageRepository;

    @Value("https://${cloud.aws.s3.bucket}.s3.ap-northeast-2.amazonaws.com/")
    private String S3_URL;


    // 단일 파일을 저장한다.
    @Transactional
    public String createImage(MultipartFile file, ImageDomain domain, Long memberId) throws IOException {
        String fileName = s3Service.upload(file, ImageDomain.PET, memberId);
        Image image = Image.from(domain, S3_URL + fileName, 1, memberId);
        imageRepository.save(image);

        return image.getPath();
    }


    // 단일 파일을 수정한다.
    @Transactional
    public String modifyProfileImage(MultipartFile file, ImageDomain domain, @PathVariable Long petId, Long memberId) throws IOException {
        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new PetException(PetErrorCode.PET_NOT_FOUND));

        // 기존 파일을 삭제하고 S3에서도 삭제한다,
        String fileNameForS3 = pet.getProfileImageUrl().replace(S3_URL, "");
        s3Service.delete(fileNameForS3);
        imageRepository.deleteImage(pet.getProfileImageUrl(), petId);

        // 새로운 파일을 저장하고 S3에 업로드한다.
        String fileName = s3Service.upload(file, ImageDomain.PET, memberId);
        Image image = Image.from(domain, S3_URL + fileName, 1, memberId);
        image.modifyDomainId(pet.getId());
        imageRepository.save(image);

        pet.modifyProfileImageUrl(image.getPath());
        return image.getPath();
    }


    // 단일 파일을 삭제한다.
    @Transactional
    public void deleteImage(DeleteImageRequest request, Long petId) {
        String fileNameForS3 = request.profileImageUrl().replace(S3_URL, "");
        s3Service.delete(fileNameForS3);
        imageRepository.deleteImage(request.profileImageUrl(), petId);
    }


    // 이미지 데이터의 domainId를 수정한다.
    @Transactional
    public void modifyDomainId(Pet pet) {
        Image image = imageRepository.getImage(pet.getProfileImageUrl());
        image.modifyDomainId(pet.getId());
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
