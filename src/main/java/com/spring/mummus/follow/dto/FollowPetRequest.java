package com.spring.mummus.follow.dto;

import com.spring.mummus.follow.entity.Follow;

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
