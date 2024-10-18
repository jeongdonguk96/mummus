package com.spring.mummus.follow.service;

import com.spring.mummus.common.AbstractTest;
import com.spring.mummus.fixture.FollowFixture;
import com.spring.mummus.fixture.MemberFixture;
import com.spring.mummus.fixture.PetFixture;
import com.spring.mummus.follow.dto.FollowPetRequest;
import com.spring.mummus.follow.entity.Follow;
import com.spring.mummus.member.entity.Member;
import com.spring.mummus.pet.entity.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FollowServiceTest extends AbstractTest {

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
        pet1 = savePet(1L, 1L);
        pet2 = savePet(2L, 1L);
        pet3 = savePet(3L, 2L);
        pet4 = savePet(4L, 2L);
        pet5 = savePet(5L, 3L);
        pet6 = savePet(6L, 3L);
    }


    @Test
    @DisplayName("팔로우가 정상적으로 등록된다.")
    void followPetTest() {
        // given
        FollowPetRequest followRequest = new FollowPetRequest(2L);

        // when
        followService.followPet(followRequest, member1.getId());
        Follow follow = followRepository.findById(1L).get();

        // then
        assertThat(follow.getFollowerMemberId()).isEqualTo(member1.getId());
        assertThat(follow.getFollowingPetId()).isEqualTo(followRequest.followingPetId());
    }


    @Test
    @DisplayName("언팔로우가 정상적으로 처리된다.")
    void unfollowPetTest() {
        // given
        saveFollow(member1.getId(), pet3.getId());
        saveFollow(member1.getId(), pet4.getId());
        FollowPetRequest followRequest = new FollowPetRequest(3L);

        // when
        followService.unfollowPet(followRequest, member1.getId());
        List<Follow> follows = followRepository.findAll();

        // then
        assertThat(follows).hasSize(1);
        assertThat(follows.get(0).getFollowingPetId()).isEqualTo(4L);
    }


    @Test
    @DisplayName("내가 팔로우하는 강아지가 정상적으로 조회된다.")
    void getFollowingPetsTest() {
        // given
        saveFollow(1L, 2L);
        saveFollow(1L, 3L);

        // when
        List<Pet> followingPets = followService.getFollowingPets(1L);

        // then
        assertThat(followingPets).hasSize(2);
        assertThat(followingPets.get(0).getId()).isEqualTo(2L);
        assertThat(followingPets.get(1).getId()).isEqualTo(3L);
    }


    @Test
    @DisplayName("내 강아지를 팔로우하는 강아지가 정상적으로 조회된다.")
    void getFollowerPetsByPetTest() {
        // given
        saveFollow(1L, 3L);
        saveFollow(1L, 5L);
        saveFollow(2L, 5L);
        saveFollow(2L, 6L);

        // when
        List<Pet> followerPets = followService.getFollowerPetsByPet(5L);

        // then
        assertThat(followerPets).hasSize(4);
        assertThat(followerPets.get(0).getMemberId()).isEqualTo(1L);
        assertThat(followerPets.get(1).getMemberId()).isEqualTo(1L);
        assertThat(followerPets.get(2).getMemberId()).isEqualTo(2L);
        assertThat(followerPets.get(3).getMemberId()).isEqualTo(2L);
    }


    @Test
    @DisplayName("나를 팔로우하는 강아지들이 정상적으로 조회된다")
    void getFollowerPetsByMemberTest() {
        // given
        saveFollow(1L, 3L);
        saveFollow(1L, 6L);
        saveFollow(2L, 5L);
        saveFollow(2L, 6L);

        // when
        Set<Pet> followerPets = followService.getFollowerPetsByMember(3L);

        // then
        assertThat(followerPets)
                .hasSize(4)
                .containsExactlyInAnyOrder(pet1, pet2, pet3, pet4);
    }


    @Test
    @DisplayName("내가 팔로우하는 강아지의 수가 정상적으로 조회된다.")
    void countFollowingPetsTest() {
        // given
        saveFollow(1L, 2L);
        saveFollow(2L, 1L);
        saveFollow(3L, 1L);
        saveFollow(3L, 2L);

        // when
        Long followingCount = followService.countFollowingPets(3L);

        // then
        assertThat(followingCount).isEqualTo(2);
    }


    @Test
    @DisplayName("내 강아지를 팔로우하는 강아지의 수가 정상적으로 조회된다.")
    void countFollowerPetsTest() {
        // given
        saveFollow(1L, 2L);
        saveFollow(1L, 3L);
        saveFollow(2L, 1L);
        saveFollow(3L, 1L);
        saveFollow(3L, 2L);

        // when
        Long followerCount = followService.countFollowerPets(3L);

        // then
        assertThat(followerCount).isEqualTo(1);
    }


    private Member saveMember(Long id) {
        Member member = MemberFixture.createMember(id);
        return memberRepository.save(member);
    }


    private Pet savePet(Long id, Long memberId) {
        Pet pet = PetFixture.createPet(id, memberId);
        return petRepository.save(pet);
    }


    private Follow saveFollow(Long memberId, Long petId) {
        Follow follow = FollowFixture.createFollow(memberId, petId);
        return followRepository.save(follow);
    }

}