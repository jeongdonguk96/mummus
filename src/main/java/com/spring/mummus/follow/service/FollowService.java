package com.spring.mummus.follow.service;

import com.spring.mummus.follow.dto.FollowPetRequest;
import com.spring.mummus.follow.entity.Follow;
import com.spring.mummus.follow.repository.FollowRepository;
import com.spring.mummus.member.service.MemberService;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.repository.PetRepository;
import com.spring.mummus.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

    private final PetService petService;
    private final MemberService memberService;
    private final PetRepository petRepository;
    private final FollowRepository followRepository;


    // 내가 팔로우하는 강아지를 조회한다.
    @Transactional(readOnly = true)
    public List<Pet> getFollowingPets(Long memberId) {
        // TODO: 프론트에서 사용할 정보만 담아 DTO로 변환하여 응답
        return petRepository.getFollowingPets(memberId);
    }


    // 내 강아지를 팔로우하는 강아지를 조회한다.
    @Transactional(readOnly = true)
    public List<Pet> getFollowerPets(Long petId) {
        // TODO: 프론트에서 사용할 정보만 담아 DTO로 변환하여 응답
        return petRepository.getFollowerPets(petId);
    }


    // 내가 팔로우하는 강아지의 수를 조회한다.
    @Transactional(readOnly = true)
    public Long countFollowingPets(Long memberId) {
        return followRepository.countFollowingPets(memberId);
    }


    // 내 강아지를 팔로우하는 강아지의 수를 조회한다.
    @Transactional(readOnly = true)
    public Long countFollowerPets(Long petId) {
        return followRepository.countFollowerPets(petId);
    }


    // 다른 강아지를 팔로우한다.
    @Transactional
    public Pet followPet(FollowPetRequest request) {
        memberService.findById(request.followerMemberId());
        Pet followingPet = petService.findById(request.followingPetId());

        Follow newFollow = request.from();
        followRepository.save(newFollow);

        return followingPet;
    }

}
