package com.spring.mummus.pet.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.mummus.pet.entity.Pet;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.spring.mummus.follow.entity.QFollow.follow;
import static com.spring.mummus.pet.entity.QPet.pet;

@RequiredArgsConstructor
public class PetRepositoryQuerydslImpl implements PetRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Long> findMyPets(Long memberId) {
        return queryFactory
                .select(pet.id)
                .from(pet)
                .where(pet.memberId.eq(memberId))
                .fetch();
    }


    @Override
    public List<Pet> searchPet(String keyword) {
        return queryFactory
                .selectFrom(pet)
                .where(pet.name.containsIgnoreCase(keyword))
                .fetch();
    }


    @Override
    public List<Pet> getFollowingPets(Long memberId) {
        return queryFactory
                .select(pet)
                .from(follow)
                .join(pet).on(pet.id.eq(follow.followingPetId))
                .where(follow.followerMemberId.eq(memberId))
                .fetch();
    }


    @Override
    public List<Pet> getFollowerPets(Long petId) {
        return queryFactory
                .select(pet)
                .from(follow)
                .join(pet).on(pet.memberId.eq(follow.followerMemberId))
                .where(follow.followingPetId.eq(petId))
                .fetch();
    }

}
