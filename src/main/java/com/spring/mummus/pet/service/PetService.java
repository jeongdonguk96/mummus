package com.spring.mummus.pet.service;

import com.spring.mummus.exception.enums.PetErrorCode;
import com.spring.mummus.exception.exception.PetException;
import com.spring.mummus.image.service.ImageService;
import com.spring.mummus.pet.dto.CreatePetRequest;
import com.spring.mummus.pet.dto.GetMyPetsResponse;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.spring.mummus.exception.enums.PetErrorCode.DUPLICATED_PET;

@Service
@RequiredArgsConstructor
public class PetService {

    private final ImageService imageService;
    private final PetRepository petRepository;


    // 강아지를 등록한다.
    @Transactional
    public Pet createPet(CreatePetRequest request, Long memberId) {
        checkDuplicatedPet(request, memberId);
        Pet pet = petRepository.save(request.from(memberId));
        imageService.modifyDomainId(pet);

        return pet;
    }


    // 다른 강아지를 조회한다.
    @Transactional
    public Pet getPet(Long petId) {
        return petRepository.findById(petId).orElseThrow(
                () -> new PetException(PetErrorCode.PET_NOT_FOUND));
    }


    // 사용자의 강아지를 조회한다.
    @Transactional
    public List<GetMyPetsResponse> getMyPets(Long memberId) {
        List<GetMyPetsResponse> myPets = petRepository.findMyPets(memberId);

        if (myPets.isEmpty()) {
            throw new PetException(PetErrorCode.MEMBER_HAS_NO_PETS);
        }

        return myPets;
    }


    // 강아지를 삭제한다.
    @Transactional
    public void deletePet(Long petId) {
        petRepository.deletePet(petId);
    }


    // 강아지 존재 여부를 확인한다.
    @Transactional(readOnly = true)
    public Pet findById(Long id) {
        return petRepository.findById(id).orElseThrow(
                () -> new PetException(PetErrorCode.PET_NOT_FOUND));
    }


    // 강아지 등록 시 중복체크를 진행한다.
    private void checkDuplicatedPet(CreatePetRequest request, Long memberId) {
        if (petRepository.existsByNameAndMemberId(request.name(), memberId)) {
            throw new PetException(DUPLICATED_PET);
        }
    }

}
