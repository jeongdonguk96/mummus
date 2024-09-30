package com.spring.mummus.pet.service;

import com.spring.mummus.exception.enums.PetErrorCode;
import com.spring.mummus.exception.exception.PetException;
import com.spring.mummus.pet.dto.RegisterPetRequest;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    // 내가 팔로잉하는 강아지들을 가져온다.
    @Transactional(readOnly = true)
    public Set<Pet> getFollowingPets(Long memberId) {
        return new HashSet<>(petRepository.getFollowingPets(memberId));
    }


    // 나를 팔로우하는 강아지들을 가져온다.
    @Transactional(readOnly = true)
    public Set<Pet> getFollowerPets(Long memberId) {
        List<Long> myPetIds = petRepository.findMyPets(memberId);
        Set<Pet> followerPets = new HashSet<>();
        for (Long myPetId : myPetIds) {
            List<Pet> tempFollowerPets = petRepository.getFollowerPets(myPetId);
            followerPets.addAll(tempFollowerPets);
        }

        return followerPets;
    }


    // 강아지의 팔로워 수를 증가시킨다.
    @Transactional
    public void increaseFollowerCount(Pet pet) {
        pet.increaseFollowerCount();
    }


    // 강아지 등록 시 중복체크를 진행한다.
    public void checkDuplicatedPet(RegisterPetRequest registerPetRequest) {
        if (petRepository.existsByNameAndMemberId(registerPetRequest.getName(), registerPetRequest.getMemberId())) {
            throw new PetException(DUPLICATED_PET);
        }
    }


    // 강아지 존재 여부를 확인한다.
    public Pet findById(Long id) {
        return petRepository.findById(id).orElseThrow(
                () -> new PetException(PetErrorCode.PET_NOT_FOUND));
    }

}
