package com.spring.mummus.follow.domain.dto;

import com.spring.mummus.follow.domain.entity.Follow;

public record FollowPetRequest(
        Long followerPetId,
        Long followingPetId
) {

    public Follow from() {
        return Follow.builder()
                .followerPetId(this.followerPetId)
                .followingPetId(this.followingPetId)
                .build();
    }
}
