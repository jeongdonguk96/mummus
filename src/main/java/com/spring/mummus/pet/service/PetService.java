package com.spring.mummus.pet.service;

import com.spring.mummus.exception.exception.PetException;
import com.spring.mummus.pet.domain.dto.RegisterPetRequest;
import com.spring.mummus.pet.domain.entity.Pet;
import com.spring.mummus.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.spring.mummus.exception.enums.PetErrorCode.DUPLICATED_PET;

@Service
@RequiredArgsConstructor
public class PetService {
//    private final PetReference petReference;
    private final PetRepository petRepository;

    @Transactional
    public Pet registerPet(RegisterPetRequest registerPetRequest) {
        checkDuplicatedPet(registerPetRequest);

        return petRepository.save(registerPetRequest.toEntity());
    }

    public void checkDuplicatedPet(RegisterPetRequest registerPetRequest) {
        if (petRepository.existsByNameAndMemberId(registerPetRequest.getName(), registerPetRequest.getMemberId())) {
            throw new PetException(DUPLICATED_PET);
        }
    }
}
