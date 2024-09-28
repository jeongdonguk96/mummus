package com.spring.mummus.search.service;

import com.spring.mummus.member.repository.MemberRepository;
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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final MemberService memberService;
    private final PetRepository petRepository;
    private final SearchRepository searchRepository;


    // 검색 기록을 저장한다.
    @Transactional
    public void saveSearch(SearchRequest request) {
        memberService.findMemberById(request.memberId());

        Search newSearch = request.from();
        searchRepository.save(newSearch);
    }


    // 검색어에 맞는 강아지를 찾는다.
    @Transactional(readOnly = true)
    public List<Pet> searchPet(SearchRequest request) {
        return petRepository.searchPet(request.keyword());
    }

}
