package com.spring.mummus.follow.repository;

import com.spring.mummus.follow.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryQuerydsl {
}
