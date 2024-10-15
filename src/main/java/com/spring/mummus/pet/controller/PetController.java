package com.spring.mummus.pet.controller;

import com.spring.mummus.image.enums.ImageDomain;
import com.spring.mummus.image.service.ImageService;
import com.spring.mummus.pet.dto.RegisterPetRequest;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    private final PetService petService;
    private final ImageService imageService;


    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void register(
            @RequestPart(name = "request") RegisterPetRequest request,
            @RequestPart(name = "files") List<MultipartFile> files
    ) throws IOException {

        Pet pet = petService.registerPet(request);
        imageService.upload(files, ImageDomain.PET, request.getMemberId());
        imageService.insert(files, ImageDomain.PET, pet.getId(), request.getMemberId());
    }

}
