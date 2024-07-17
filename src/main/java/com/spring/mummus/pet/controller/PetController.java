package com.spring.mummus.pet.controller;

import com.spring.mummus.pet.domain.dto.RegisterPetRequest;
import com.spring.mummus.pet.domain.entity.Pet;
import com.spring.mummus.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @PostMapping("/register")
    public ResponseEntity<Pet> register(@RequestBody RegisterPetRequest registerPetRequest) {
        return ResponseEntity.ok(petService.registerPet(registerPetRequest));
    }
}
