package com.spring.mummus.controller;

import com.spring.mummus.domain.dto.pet.RegisterPetDto;
import com.spring.mummus.domain.entity.Pet;
import com.spring.mummus.service.PetService;
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
    public ResponseEntity<Pet> register(@RequestBody RegisterPetDto registerPetDto) {
        return ResponseEntity.ok(petService.registerPet(registerPetDto));
    }
}
