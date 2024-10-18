package com.spring.mummus.image.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageRepositoryQuerydslImpl implements ImageRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

}
