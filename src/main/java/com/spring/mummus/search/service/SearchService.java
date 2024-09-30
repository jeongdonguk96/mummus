package com.spring.mummus.search.service;

import com.spring.mummus.follow.repository.FollowRepository;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final MemberService memberService;
    private final PetRepository petRepository;
    private final FollowRepository followRepository;
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
        // 1. 이름으로 전체 강아지를 검색한다.
        List<Pet> searchedPets = petRepository.searchPet(request.keyword());

        // TODO: 얘네들 Set으로 바꿔야함
        // 2. 내가 팔로잉하거나 나를 팔로우하는 강아지를 찾는다.
        List<Pet> followingPets = petRepository.getFollowingPets(memberId);
        List<Long> myPetIds = petRepository.findMyPets(memberId);
        List<Pet> followerPets = new ArrayList<>();
        for (Long myPetId : myPetIds) {
            List<Pet> tempFollowerPets = petRepository.getFollowerPets(myPetId);
            followerPets.addAll(tempFollowerPets);
        }


        // 3. 우선순위에 따라 리스트를 재정렬한다. (1순위 : 팔로잉/팔로워, 2순위: 팔로워 수)
        return searchedPets.stream()
                .sorted(Comparator
                        .comparing(followerPets)
                        .thenComparing(Pet::getFollowerCount).reversed())
                .collect(Collectors.toList());
    }

}
