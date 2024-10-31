package com.spring.mummus.pet.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.mummus.pet.dto.GetMyPetsResponse;
import com.spring.mummus.pet.dto.QGetMyPetsResponse;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.enums.Gender;
import com.spring.mummus.pet.enums.PetType;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.spring.mummus.follow.entity.QFollow.follow;
import static com.spring.mummus.pet.entity.QPet.pet;

@RequiredArgsConstructor
public class PetRepositoryQuerydslImpl implements PetRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<GetMyPetsResponse> findMyPets(Long memberId) {
        return queryFactory
                .select(new QGetMyPetsResponse(
                        pet.name,
                        pet.age,
                        pet.birth,
                        pet.gender,
                        pet.petType,
                        pet.profileImageUrl,
                        pet.followerCount
                ))
                .from(pet)
                .where(pet.memberId.eq(memberId))
                .fetch();
    }

    @Override
    public List<Long> findMyPetsIds(Long memberId) {
        return queryFactory
                .select(pet.id)
                .from(pet)
                .where(pet.memberId.eq(memberId))
                .fetch();
    }


    @Override
    public List<Pet> searchPets(String keyword, Long memberId) {
        return queryFactory
                .selectFrom(pet)
                .where(pet.name.containsIgnoreCase(keyword)
                        .and(memberId != null ? pet.memberId.eq(memberId).not() : null)
                )
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


    @Override
    public void modifyPetName(Long petId, String name) {
        queryFactory
                .update(pet)
                .set(pet.name, name)
                .where(pet.id.eq(petId))
                .execute();
    }

    @Override
    public void modifyPetAge(Long petId, int age) {
        queryFactory
                .update(pet)
                .set(pet.age, age)
                .where(pet.id.eq(petId))
                .execute();
    }

    @Override
    public void modifyPetBirth(Long petId, String birth) {
        queryFactory
                .update(pet)
                .set(pet.birth, birth)
                .where(pet.id.eq(petId))
                .execute();
    }

    @Override
    public void modifyPetGender(Long petId, Gender gender) {
        queryFactory
                .update(pet)
                .set(pet.gender, gender)
                .where(pet.id.eq(petId))
                .execute();
    }

    @Override
    public void modifyPetType(Long petId, PetType petType) {
        queryFactory
                .update(pet)
                .set(pet.petType, petType)
                .where(pet.id.eq(petId))
                .execute();
    }


    @Override
    public void deletePet(Long petId) {
        queryFactory
                .delete(pet)
                .where(pet.id.eq(petId))
                .execute();
    }

}
