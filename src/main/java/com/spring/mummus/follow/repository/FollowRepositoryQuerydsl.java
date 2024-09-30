package com.spring.mummus.follow.repository;

public interface FollowRepositoryQuerydsl {
    Long countFollowingPets(Long memberId);
    Long countFollowerPets(Long petId);
}
