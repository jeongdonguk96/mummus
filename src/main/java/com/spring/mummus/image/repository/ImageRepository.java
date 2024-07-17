package com.spring.mummus.image.repository;

import com.spring.mummus.image.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
