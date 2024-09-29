package com.spring.mummus.follow.dto;

import com.spring.mummus.follow.entity.Follow;

public record FollowPetRequest(
        Long followerMemberId,
        Long followingPetId
) {

    public Follow from() {
        return Follow.builder()
                .followerMemberId(this.followerMemberId)
                .followingPetId(this.followingPetId)
                .build();
    }
}
