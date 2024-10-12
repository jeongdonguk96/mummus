package com.spring.mummus.fixture;

import com.spring.mummus.follow.entity.Follow;

public class FollowFixture {

    public static Follow createFollow(Long memberId, Long petId) {
        return Follow.builder()
                .followerMemberId(memberId)
                .followingPetId(petId)
                .build();
    }

}
