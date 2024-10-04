package com.spring.mummus.follow.service;

import com.spring.mummus.common.AbstractTest;
import com.spring.mummus.follow.dto.FollowPetRequest;
import com.spring.mummus.follow.entity.Follow;
import com.spring.mummus.follow.repository.FollowRepository;
import com.spring.mummus.member.dto.MemberSignUpRequest;
import com.spring.mummus.member.entity.Member;
import com.spring.mummus.member.service.MemberService;
import com.spring.mummus.pet.dto.RegisterPetRequest;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.repository.PetRepository;
import com.spring.mummus.pet.service.PetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.spring.mummus.pet.enums.Gender.FEMALE;
import static com.spring.mummus.pet.enums.PetType.JINDO_DOG;
import static com.spring.mummus.pet.enums.PetType.SHIVA_INU;
import static org.assertj.core.api.Assertions.assertThat;

class FollowServiceTest extends AbstractTest {

    @Autowired PetService petService;
    @Autowired FollowService followService;
    @Autowired MemberService memberService;
    @Autowired PetRepository petRepository;
    @Autowired FollowRepository followRepository;


    @Test
    @DisplayName("팔로우가 정상적으로 등록된다.")
    void followPetTest() {
        // given
        MemberSignUpRequest memberRequest1 = new MemberSignUpRequest("test@naver.com1", "password1", "testName1", "testAddress1", "testPhoneNumber1");
        MemberSignUpRequest memberRequest2 = new MemberSignUpRequest("test@naver.com2", "password2", "testName2", "testAddress2", "testPhoneNumber2");
        Member member = memberService.signUp(memberRequest1);
        memberService.signUp(memberRequest2);

        RegisterPetRequest petRequest1 = new RegisterPetRequest("bona", 4, "2020-08-01", FEMALE, JINDO_DOG, 1L);
        RegisterPetRequest petRequest2 = new RegisterPetRequest("mingki", 0, "2024-09-01", FEMALE, SHIVA_INU, 2L);
        petService.registerPet(petRequest1);
        petService.registerPet(petRequest2);

        FollowPetRequest followRequest = new FollowPetRequest(1L, 2L);

        // when
        Pet pet = followService.followPet(followRequest);
        Follow follow = followRepository.findById(1L).get();

        // then
        assertThat(follow.getFollowerMemberId()).isEqualTo(member.getId());
        assertThat(follow.getFollowingPetId()).isEqualTo(pet.getId());
    }


}