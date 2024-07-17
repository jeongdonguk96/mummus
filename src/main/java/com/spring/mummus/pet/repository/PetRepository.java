package com.spring.mummus.pet.repository;

import com.spring.mummus.pet.domain.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Boolean existsByNameAndMemberId(String name, Long memberId);
}
