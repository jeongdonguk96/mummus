package com.spring.mummus.pet.controller;

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

    private final PetService petService;


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 강아지를 등록한다.
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createPet(
            @RequestPart(name = "request") RegisterPetRequest request,
            @RequestPart(name = "file") MultipartFile file
    ) throws IOException {

        Long memberId = 1L;
        Pet pet = petService.createPet(request, file, memberId);
    }


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 사용자의 강아지를 조회한다.
    @GetMapping("/{memberId}")
    public void getMyPets(@PathVariable(name = "memberId") Long memberId) {
        List<Pet> pets = petService.getPets(memberId);
    }

}
