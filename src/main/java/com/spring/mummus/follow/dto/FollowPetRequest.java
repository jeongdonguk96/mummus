package com.spring.mummus.follow.dto;

import com.spring.mummus.follow.entity.Follow;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "ddd")
public record FollowPetRequest(
        @Schema(description = "팔로우할 강아지의 ID", example = "1")
        Long followingPetId
) {

    public Follow from(Long followerMemberId) {
        return Follow.builder()
                .followerMemberId(followerMemberId)
                .followingPetId(this.followingPetId)
                .build();
    }
}
