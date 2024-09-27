package com.spring.mummus.follow.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.spring.mummus.follow.domain.entity.QFollow.follow;

@RequiredArgsConstructor
public class FollowRepositoryQuerydslImpl implements FollowRepositoryQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Long> getFollowingPetsId(Long id) {
        return jpaQueryFactory
                .select(follow.followingPetId)
                .from(follow)
                .where(follow.followerPetId.eq(id))
                .fetch();
    }


    @Override
    public List<Long> getFollowerPetsId(Long id) {
        return jpaQueryFactory
                .select(follow.followerPetId)
                .from(follow)
                .where(follow.followingPetId.eq(id))
                .fetch();
    }


    @Override
    public Long countFollowingPets(Long id) {
        return jpaQueryFactory
                .select(follow.followingPetId.count())
                .from(follow)
                .where(follow.followerPetId.eq(id))
                .fetchOne();
    }


    @Override
    public Long countFollowerPets(Long id) {
        return jpaQueryFactory
                .select(follow.followerPetId.count())
                .from(follow)
                .where(follow.followingPetId.eq(id))
                .fetchOne();
    }

}
