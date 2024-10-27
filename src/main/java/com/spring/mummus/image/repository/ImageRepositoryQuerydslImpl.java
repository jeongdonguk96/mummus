package com.spring.mummus.image.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.mummus.image.entity.Image;
import lombok.RequiredArgsConstructor;

import static com.spring.mummus.image.entity.QImage.image;

@RequiredArgsConstructor
public class ImageRepositoryQuerydslImpl implements ImageRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Image getImage(String path) {
        return queryFactory
                .selectFrom(image)
                .where(image.path.eq(path))
                .fetchFirst();
    }

    @Override
    public void deleteImage(String path, Long petId) {
        queryFactory
                .delete(image)
                .where(image.domainId.eq(petId)
                        .and(image.path.eq(path)))
                .execute();
    }

}
