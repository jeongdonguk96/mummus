package com.spring.mummus.pet.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.spring.mummus.pet.enums.Gender;
import com.spring.mummus.pet.enums.PetType;


public record GetMyPetsResponse(
        String name,
        int age,
        String birth,
        Gender gender,
        PetType petType,
        String profileImageUrl,
        int followerCount
) {

    @QueryProjection
    public GetMyPetsResponse {}

}
