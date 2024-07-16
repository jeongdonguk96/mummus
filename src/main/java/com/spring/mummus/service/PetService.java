package com.spring.mummus.service;

import com.spring.mummus.domain.dto.pet.RegisterPetDto;
import com.spring.mummus.domain.entity.Pet;
import com.spring.mummus.repository.PetRepository;
import com.spring.mummus.service.reference.PetReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetReference petReference;
    private final PetRepository petRepository;

    public Pet registerPet(RegisterPetDto registerPetDto) {
        petReference.checkDuplicatedPet(registerPetDto.getName(), registerPetDto.getMemberId());

        return petRepository.save(Pet.builder()
            .name(registerPetDto.getName())
            .age(registerPetDto.getAge())
            .gender(registerPetDto.getGender())
            .petType(registerPetDto.getPetType())
            .memberId(registerPetDto.getMemberId())
            .build());
    }
}
