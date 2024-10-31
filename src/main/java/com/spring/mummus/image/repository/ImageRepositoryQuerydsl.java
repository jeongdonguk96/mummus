package com.spring.mummus.image.repository;

import com.spring.mummus.image.entity.Image;

public interface ImageRepositoryQuerydsl {
    Image getImage(String path);
    void deleteImage(String path, Long petId);
    void rollbackImage(String path);
}
