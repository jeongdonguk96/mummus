package com.spring.mummus.pet.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.entity.QPet;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.spring.mummus.pet.entity.QPet.pet;

@RequiredArgsConstructor
public class PetRepositoryQuerydslImpl implements PetRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Pet> searchPet(String keyword) {
        return queryFactory
                .selectFrom(pet)
                .where(pet.name.containsIgnoreCase(keyword))
                .fetch();
    }

}
