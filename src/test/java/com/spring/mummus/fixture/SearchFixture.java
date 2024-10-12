package com.spring.mummus.fixture;

import com.spring.mummus.search.entity.Search;

public class SearchFixture {

    public static Search createSearch(String keyword, Long memberId) {
        return Search.builder()
                .keyword(keyword)
                .memberId(memberId)
                .build();
    }

}
