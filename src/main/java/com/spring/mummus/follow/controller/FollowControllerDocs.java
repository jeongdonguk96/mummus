package com.spring.mummus.follow.controller;

import com.spring.mummus.follow.dto.FollowPetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "팔로우 API")
public interface FollowControllerDocs {

    @Operation(
            summary = "팔로우",
            description = "다른 강아지를 팔로우한다.",
            parameters = {
                    @Parameter(name = "memberId", description = "팔로우하는 사용자의 ID", example = "1", required = true)
            }
    )
    public void followPet(FollowPetRequest request, Long memberId);
    public void unfollowPet(FollowPetRequest request, Long memberId);
    public void getFollowingPets(Long memberId);
    public void countFollowingPets(Long memberId);
    public void getFollowerPets(Long petId);
    public void countFollowerPets(Long petId);

}
