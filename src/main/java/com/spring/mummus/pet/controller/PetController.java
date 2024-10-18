package com.spring.mummus.pet.controller;

import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.image.service.ImageService;
import com.spring.mummus.image.service.S3Service;
import com.spring.mummus.pet.dto.RegisterPetRequest;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final S3Service s3Service;
    private final PetService petService;
    private final ImageService imageService;


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 강아지를 등록한다.
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createPet(
            @RequestPart(name = "request") RegisterPetRequest request,
            @RequestPart(name = "file") MultipartFile file
    ) throws IOException {

        Long memberId = 1L;
        petService.checkDuplicatedPet(request, memberId);
        Pet pet = petService.registerPet(request, memberId);
        s3Service.upload(file, ImageDomain.PET, memberId);
        String imageUrl = imageService.createImage(file, ImageDomain.PET, pet.getId(), memberId);
        petService.updateProfileImage(pet, imageUrl);
    }


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 사용자의 강아지를 조회한다.
    @GetMapping("/{memberId}")
    public void getMyPets(@PathVariable(name = "memberId") Long memberId) {
        List<Pet> pets = petService.getPets(memberId);

        if (pets.isEmpty()) {
            return;
        }

        List<String> imageUrls = s3Service.getFiles(pets);
    }

}
