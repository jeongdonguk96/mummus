package com.spring.mummus.search.controller;

import com.spring.mummus.search.dto.SearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "검색 API")
public interface SearchControllerDocs {

    @Operation(summary = "검색", description = "다른 강아지를 검색한다.")
    @Parameter(name = "keyword", description = "검색할 강아지의 이름", example = "보리", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public void searchPet(SearchRequest request);

}
