package com.spring.mummus.follow.repository;

import java.util.List;

public interface FollowRepositoryQuerydsl {
    List<Long> getFollowingPetsId(Long id);
    List<Long> getFollowerPetsId(Long id);
    Long countFollowingPets(Long id);
    Long countFollowerPets(Long id);
}
