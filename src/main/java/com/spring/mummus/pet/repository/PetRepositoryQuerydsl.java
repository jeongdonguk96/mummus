package com.spring.mummus.pet.repository;

import com.spring.mummus.pet.dto.GetMyPetsResponse;
import com.spring.mummus.pet.entity.Pet;

import java.util.List;

public interface PetRepositoryQuerydsl {
    List<GetMyPetsResponse> findMyPets(Long memberId);
    List<Long> findMyPetsIds(Long memberId);
    List<Pet> searchPets(String keyword, Long memberId);
    List<Pet> getFollowingPets(Long memberId);
    List<Pet> getFollowerPets(Long petId);
    void deletePet(Long petId);
}
