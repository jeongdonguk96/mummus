package com.spring.mummus.pet.controller;

import com.spring.mummus.pet.dto.CreatePetRequest;
import com.spring.mummus.pet.dto.GetMyPetsResponse;
import com.spring.mummus.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 강아지를 등록한다.
    @PostMapping()
    public void createPet(@RequestBody CreatePetRequest request) {
        Long memberId = 1L;
        petService.createPet(request, memberId);
    }


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 사용자의 강아지를 조회한다.
    @GetMapping("/my-pets")
    public List<GetMyPetsResponse> getMyPets() {
        Long memberId = 1L;
        return petService.getMyPets(memberId);
    }

}
