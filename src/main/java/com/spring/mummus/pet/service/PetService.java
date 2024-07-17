package com.spring.mummus.pet.service;

import com.spring.mummus.pet.domain.dto.RegisterPetRequest;
import com.spring.mummus.pet.domain.entity.Pet;
import com.spring.mummus.pet.service.reference.PetReference;
import com.spring.mummus.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetReference petReference;
    private final PetRepository petRepository;

    public Pet registerPet(RegisterPetRequest registerPetRequest) {
        petReference.checkDuplicatedPet(registerPetRequest);

        return petRepository.save(registerPetRequest.toEntity());
    }
}
