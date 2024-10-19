package com.spring.mummus.search.service;

import com.spring.mummus.common.AbstractTest;
import com.spring.mummus.fixture.FollowFixture;
import com.spring.mummus.fixture.MemberFixture;
import com.spring.mummus.fixture.PetFixture;
import com.spring.mummus.fixture.SearchFixture;
import com.spring.mummus.follow.entity.Follow;
import com.spring.mummus.member.entity.Member;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.search.dto.SearchRequest;
import com.spring.mummus.search.entity.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SearchServiceTest extends AbstractTest {

    private Member member1;
    private Member member2;
    private Member member3;
    private Pet pet1;
    private Pet pet2;
    private Pet pet3;
    private Pet pet4;
    private Pet pet5;
    private Pet pet6;


    @BeforeEach
    void setUp() {
        member1 = saveMember(1L);
        member2 = saveMember(2L);
        member3 = saveMember(3L);
        pet1 = savePet(1L, 1L, "일번이", "test.com/url");
        pet2 = savePet(2L, 1L, "이번이", "test.com/url");
        pet3 = savePet(3L, 2L, "삼번이", "test.com/url");
        pet4 = savePet(4L, 2L, "사번이", "test.com/url");
        pet5 = savePet(5L, 3L, "오번이", "test.com/url");
        pet6 = savePet(6L, 3L, "육번이", "test.com/url");
        saveFollow(1L, 5L);
        saveFollow(1L, 6L);
    }


    @Test
    @DisplayName("강아지가 정상적으로 검색된다.")
    void searchPetTest() {
        // given
        SearchRequest request1 = new SearchRequest("번");
        SearchRequest request2 = new SearchRequest("오번");

        // when
        List<Pet> pets1 = searchService.searchPet(request1, member1.getId());
        List<Pet> pets2 = searchService.searchPet(request2, member1.getId());

        // then
        assertThat(pets1).hasSize(4);
        assertThat(pets2).hasSize(1);
    }


    @Test
    @DisplayName("로그인한 사람이 검색할 경우 우선 순위를 정해 걸색 결과가 정렬된다. ")
    void sortOrderTest1() {
        // given
        SearchRequest request = new SearchRequest("번");
        pet4.increaseFollowerCount();

        // when
        List<Pet> pets = searchService.searchPet(request, member1.getId());

        // then
        assertThat(pets).hasSize(4);
        assertThat(pets.get(0).getName()).isEqualTo("오번이");
        assertThat(pets.get(1).getName()).isEqualTo("육번이");
        assertThat(pets.get(2).getName()).isEqualTo("사번이");
        assertThat(pets.get(3).getName()).isEqualTo("삼번이");
    }


    @Test
    @DisplayName("로그인하지 않은 사람이 검색할 경우 우선 순위를 정해 걸색 결과가 정렬된다. ")
    void sortOrderTest2() {
        // given
        SearchRequest request = new SearchRequest("번");
        pet4.increaseFollowerCount();
        pet4.increaseFollowerCount();
        pet3.increaseFollowerCount();
        pet2.increaseFollowerCount();

        // when
        List<Pet> pets = searchService.searchPet(request, null);

        // then
        assertThat(pets).hasSize(6);
        assertThat(pets.get(0).getName()).isEqualTo("사번이");
        assertThat(pets.get(1).getName()).isEqualTo("이번이");
        assertThat(pets.get(2).getName()).isEqualTo("삼번이");
        assertThat(pets.get(3).getName()).isEqualTo("일번이");
        assertThat(pets.get(4).getName()).isEqualTo("오번이");
        assertThat(pets.get(5).getName()).isEqualTo("육번이");
    }


    private Member saveMember(Long id) {
        Member member = MemberFixture.createMember(id);
        return memberRepository.save(member);
    }


    private Pet savePet(Long id, Long memberId, String name, String profileImageUrl) {
        Pet pet = PetFixture.createPet(id, memberId, name, profileImageUrl);
        return petRepository.save(pet);
    }


    private Follow saveFollow(Long memberId, Long petId) {
        Follow follow = FollowFixture.createFollow(memberId, petId);
        return followRepository.save(follow);
    }


    private Search saveSearch(String keyword, Long memberId) {
        Search search = SearchFixture.createSearch(keyword, memberId);
        return searchRepository.save(search);
    }

}