package com.spring.mummus.pet.repository;

import com.spring.mummus.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long>, PetRepositoryQuerydsl {
    Boolean existsByNameAndMemberId(String name, Long memberId);
}
