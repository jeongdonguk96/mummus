package com.spring.mummus.follow.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.spring.mummus.follow.entity.QFollow.follow;

@RequiredArgsConstructor
public class FollowRepositoryQuerydslImpl implements FollowRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;


    @Override
    public Long countFollowingPets(Long memberId) {
        return queryFactory
                .select(follow.followingPetId.count())
                .from(follow)
                .where(follow.followerMemberId.eq(memberId))
                .fetchOne();
    }


    @Override
    public Long countFollowerPets(Long petId) {
        return queryFactory
                .select(follow.followerMemberId.count())
                .from(follow)
                .where(follow.followingPetId.eq(petId))
                .fetchOne();
    }

    @Override
    public void deleteFollow(Long petId, Long memberId) {
        queryFactory
                .delete(follow)
                .where(follow.followingPetId.eq(petId)
                        .and(follow.followerMemberId.eq(memberId))
                )
                .execute();
    }

}
