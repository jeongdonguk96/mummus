package com.spring.mummus.image.repository;

import com.spring.mummus.image.entity.Image;

public interface ImageRepositoryQuerydsl {
    Image getImage(String path);
}
