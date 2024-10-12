package com.spring.mummus.follow.dto;

import com.spring.mummus.follow.entity.Follow;

public record FollowPetRequest(
        Long followingPetId
) {

    public Follow from(Long followerMemberId) {
        return Follow.builder()
                .followerMemberId(followerMemberId)
                .followingPetId(this.followingPetId)
                .build();
    }
}
