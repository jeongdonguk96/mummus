package com.spring.mummus.search.dto;

import com.spring.mummus.search.entity.Search;

public record SearchRequest(
        String keyword
) {

    public Search from(Long memberId) {
        return Search.builder()
                .keyword(this.keyword)
                .memberId(memberId)
                .build();
    }
}
