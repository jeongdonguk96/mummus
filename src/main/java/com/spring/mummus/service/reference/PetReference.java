package com.spring.mummus.service.reference;

import com.spring.mummus.exception.exception.PetException;
import com.spring.mummus.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.spring.mummus.exception.enums.PetErrorCode.DUPLICATED_PET;

@Service
@RequiredArgsConstructor
public class PetReference {
    private final PetRepository petRepository;

    public void checkDuplicatedPet(String petName, Long memberId) {
        if (petRepository.existsByNameAndMemberId(petName, memberId)) {
            throw new PetException(DUPLICATED_PET);
        }
    }
}
