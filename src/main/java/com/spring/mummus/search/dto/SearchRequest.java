package com.spring.mummus.search.dto;

import com.spring.mummus.search.entity.Search;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "검색 요청 DTO")
public record SearchRequest(
        @Schema(description = "검색할 강아지의 이름", example = "보리")
        String keyword
) {

    public Search from(Long memberId) {
        return Search.builder()
                .keyword(this.keyword)
                .memberId(memberId)
                .build();
    }
}
