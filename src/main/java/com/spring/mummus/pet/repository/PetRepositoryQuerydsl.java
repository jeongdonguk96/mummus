package com.spring.mummus.pet.repository;

import com.spring.mummus.pet.entity.Pet;

import java.util.List;

public interface PetRepositoryQuerydsl {
    List<Pet> findMyPets(Long memberId);
    List<Long> findMyPetsIds(Long memberId);
    List<Pet> searchPets(String keyword, Long memberId);
    List<Pet> getFollowingPets(Long memberId);
    List<Pet> getFollowerPets(Long petId);
}
