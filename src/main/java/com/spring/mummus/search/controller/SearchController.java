package com.spring.mummus.search.controller;

import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.service.PetService;
import com.spring.mummus.search.dto.SearchRequest;
import com.spring.mummus.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final PetService petService;
    private final SearchService searchService;


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    @GetMapping()
    public void searchPet(SearchRequest request, Long memberId) {
        if (memberId == null) {
        } else {
            searchService.saveSearch(request, memberId);
        }
        List<Pet> searchedPet = searchService.searchPet(request);
        Set<Pet> followingPets = petService.getFollowingPets(memberId);
        Set<Pet> followerPets = petService.getFollowerPets(memberId);
        List<Pet> searchResult = searchService.sortOrder(searchedPet, followingPets, followerPets);
    }

}
