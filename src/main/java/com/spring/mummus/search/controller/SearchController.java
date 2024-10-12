package com.spring.mummus.search.controller;

import com.spring.mummus.follow.service.FollowService;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.search.dto.SearchRequest;
import com.spring.mummus.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final FollowService followService;
    private final SearchService searchService;


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    @GetMapping()
    public void searchPet(@RequestBody SearchRequest request, Long memberId) {
        List<Pet> searchedPet;
        Set<Pet> followerPets = null;
        Set<Pet> followingPets = null;

        if (memberId == null) {
            searchedPet = searchService.searchPet(request, memberId);
        } else {
            searchService.saveSearch(request, memberId);
            searchedPet = searchService.searchPet(request, memberId);
            followerPets = followService.getFollowerPetsByMember(memberId);
            followingPets = new HashSet<>(followService.getFollowingPets(memberId));
        }

        List<Pet> searchResult = searchService.sortOrder(searchedPet, followingPets, followerPets);
    }

}
