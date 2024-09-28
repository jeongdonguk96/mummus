package com.spring.mummus.search.controller;

import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.search.dto.SearchRequest;
import com.spring.mummus.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;


    @GetMapping()
    public void searchPet(SearchRequest request) {
        searchService.saveSearch(request);
        searchService.searchPet(request);
    }
}
