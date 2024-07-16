package com.spring.mummus.repository;

import com.spring.mummus.domain.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Boolean existsPet(String name, Long memberId);
}
