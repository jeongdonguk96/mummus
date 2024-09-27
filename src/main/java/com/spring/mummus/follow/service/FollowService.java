package com.spring.mummus.follow.service;

import com.spring.mummus.exception.enums.MemberErrorCode;
import com.spring.mummus.exception.enums.PetErrorCode;
import com.spring.mummus.exception.exception.MemberException;
import com.spring.mummus.exception.exception.PetException;
import com.spring.mummus.follow.domain.dto.FollowPetRequest;
import com.spring.mummus.follow.domain.entity.Follow;
import com.spring.mummus.follow.repository.FollowRepository;
import com.spring.mummus.pet.domain.entity.Pet;
import com.spring.mummus.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

    private final PetRepository petRepository;
    private final FollowRepository followRepository;


    // 팔로잉하는 강아지를 조회한다.
    public List<Pet> getFollowingPets(Long id) {
        List<Long> followingPetsId = followRepository.getFollowingPetsId(id);
        // TODO: 프론트에서 사용할 정보만 담아 DTO로 변환하여 응답
        return petRepository.findAllById(followingPetsId);
    }


    // 팔로워 강아지를 조회한다.
    public List<Pet> getFollowerPets(Long id) {
        List<Long> followerPetsId = followRepository.getFollowerPetsId(id);
        // TODO: 프론트에서 사용할 정보만 담아 DTO로 변환하여 응답
        return petRepository.findAllById(followerPetsId);
    }


    // 팔로잉하는 강아지의 수를 조회한다.
    public Long countFollowingPets(Long id) {
        return followRepository.countFollowingPets(id);
    }


    // 팔로워 강아지의 수를 조회한다.
    public Long countFollowerPets(Long id) {
        return followRepository.countFollowerPets(id);
    }


    // 다른 강아지를 팔로우한다.
    public void followPet(FollowPetRequest request) {
        // TODO: 데이터 존재 유무 확인을 위한 별도의 클래스 생성 고려
        petRepository.findById(request.followerPetId()).orElseThrow(
                () -> new MemberException(MemberErrorCode.USER_NOT_FOUND));

        petRepository.findById(request.followingPetId()).orElseThrow(
                () -> new PetException(PetErrorCode.PET_NOT_FOUND));

        Follow newFollow = request.from();
        followRepository.save(newFollow);
    }

}
