package com.spring.mummus.search.service;

import com.spring.mummus.member.service.MemberService;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.repository.PetRepository;
import com.spring.mummus.search.dto.SearchRequest;
import com.spring.mummus.search.entity.Search;
import com.spring.mummus.search.respository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final MemberService memberService;
    private final PetRepository petRepository;
    private final SearchRepository searchRepository;


    // 검색 기록을 저장한다.
    @Transactional
    public void saveSearch(SearchRequest request, Long memberId) {
        memberService.findById(memberId);

        Search newSearch = request.from(memberId);
        searchRepository.save(newSearch);
    }


    // 검색어에 맞는 강아지를 찾는다.
    @Transactional(readOnly = true)
    public List<Pet> searchPet(SearchRequest request, Long memberId) {
        return petRepository.searchPet(request.keyword(), memberId);
    }


    // 우선 순위를 정해 걸색 결과를 정렬한다.
    @Transactional(readOnly = true)
    public List<Pet> sortOrder(List<Pet> searchedPets, Set<Pet> followerPets, Set<Pet> followingPets) {
        Set<Pet> nullCheckedFollowerPets = followerPets != null ? followerPets : Collections.emptySet();
        Set<Pet> nullCheckedFollowingPets = followingPets != null ? followingPets : Collections.emptySet();

        return searchedPets.stream()
                .sorted(Comparator
                        .comparing((Pet pet) -> nullCheckedFollowerPets.contains(pet) ||  nullCheckedFollowingPets.contains(pet))
                        .thenComparing(Pet::getFollowerCount).reversed()
                )
                .toList();
    }

}
