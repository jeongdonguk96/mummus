package com.spring.mummus.pet.repository;

import com.spring.mummus.pet.dto.GetMyPetsResponse;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.enums.Gender;
import com.spring.mummus.pet.enums.PetType;

import java.util.List;

public interface PetRepositoryQuerydsl {
    List<GetMyPetsResponse> findMyPets(Long memberId);
    List<Long> findMyPetsIds(Long memberId);
    List<Pet> searchPets(String keyword, Long memberId);
    List<Pet> getFollowingPets(Long memberId);
    List<Pet> getFollowerPets(Long petId);
    void modifyPetName(Long petId, String name);
    void modifyPetAge(Long petId, int age);
    void modifyPetBirth(Long petId, String birth);
    void modifyPetGender(Long petId, Gender gender);
    void modifyPetType(Long petId, PetType petType);
    void deletePet(Long petId);
}
