package com.spring.mummus.pet.service;

import com.spring.mummus.exception.enums.PetErrorCode;
import com.spring.mummus.exception.exception.PetException;
import com.spring.mummus.pet.dto.RegisterPetRequest;
import com.spring.mummus.pet.entity.Pet;
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


    // 강아지를 등록한다.
    @Transactional
    public Pet registerPet(RegisterPetRequest registerPetRequest) {
        checkDuplicatedPet(registerPetRequest);

        return petRepository.save(registerPetRequest.toEntity());
    }


    // 강아지의 팔로워 수를 증가시킨다.
    @Transactional
    public void increaseFollowerCount(Pet pet) {
        pet.increaseFollowerCount();
    }


    // 강아지의 팔로워 수를 하락시킨다.
    @Transactional
    public void decreaseFollowerCount(Pet pet) {
        pet.decreaseFollowerCount();
    }


    // 강아지 존재 여부를 확인한다.
    public Pet findById(Long id) {
        return petRepository.findById(id).orElseThrow(
                () -> new PetException(PetErrorCode.PET_NOT_FOUND));
    }


    // 강아지 등록 시 중복체크를 진행한다.
    private void checkDuplicatedPet(RegisterPetRequest registerPetRequest) {
        if (petRepository.existsByNameAndMemberId(registerPetRequest.getName(), registerPetRequest.getMemberId())) {
            throw new PetException(DUPLICATED_PET);
        }
    }

}
