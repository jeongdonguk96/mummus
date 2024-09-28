package com.spring.mummus.pet.repository;

import com.spring.mummus.pet.entity.Pet;

import java.util.List;

public interface PetRepositoryQuerydsl {
    List<Pet> searchPet(String keyword);
}
