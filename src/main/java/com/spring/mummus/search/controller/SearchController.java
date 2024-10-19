package com.spring.mummus.search.controller;

import com.spring.mummus.search.dto.SearchRequest;
import com.spring.mummus.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController implements SearchControllerDocs {

    private final SearchService searchService;


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 다른 강아지를 검색한다.
    @GetMapping("/search")
    public void searchPet(SearchRequest request) {
        Long memberId = 1L;

        searchService.searchPet(request, memberId);
    }

}
