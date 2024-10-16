package com.spring.mummus.image.service;

import com.spring.mummus.image.entity.Image;
import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.image.repository.ImageRepository;
import com.spring.mummus.utils.CommonUtils;
import com.spring.mummus.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;


    // 단일 파일을 저장한다.
    @Transactional
    public String createImage(MultipartFile file, ImageDomain domain, Long domainId, Long memberId) {
        String filename = extractFilename(domain, memberId, file);
        Image image = Image.from(domain, domainId, filename, 1, memberId);

        imageRepository.save(image);

        return image.getPath();
    }


    // 다중 파일을 저장한다.
    @Transactional
    public void createImage(List<MultipartFile> files, ImageDomain domain, Long domainId, Long memberId) {
        List<Image> images = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            String filename = extractFilename(domain, memberId, files.get(i));
            Image image = Image.from(domain, domainId, filename, i + 1, memberId);
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

}
