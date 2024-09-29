package com.spring.mummus.pet.repository;

import com.spring.mummus.pet.entity.Pet;

import java.util.List;

public interface PetRepositoryQuerydsl {
    List<Long> findMyPets(Long memberId);
    List<Pet> searchPet(String keyword);
    List<Pet> getFollowingPets(Long memberId);
    List<Pet> getFollowerPets(Long petId);
}
