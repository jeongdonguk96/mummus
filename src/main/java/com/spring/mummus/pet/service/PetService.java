package com.spring.mummus.pet.service;

import com.spring.mummus.pet.domain.dto.RegisterPetRequest;
import com.spring.mummus.pet.domain.entity.Pet;
import com.spring.mummus.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetReference petReference;
    private final PetRepository petRepository;

    @Transactional
    public Pet registerPet(RegisterPetRequest registerPetRequest) {
        petReference.checkDuplicatedPet(registerPetRequest);

        return petRepository.save(registerPetRequest.toEntity());
    }

}
