package com.spring.mummus.search.dto;

import com.spring.mummus.follow.entity.Follow;
import com.spring.mummus.search.entity.Search;

public record SearchRequest(
        String keyword,
        Long memberId
) {

    public Search from() {
        return Search.builder()
                .keyword(this.keyword)
                .memberId(this.memberId)
                .build();
    }
}
