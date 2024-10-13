package com.spring.mummus.follow.controller;

import com.spring.mummus.follow.dto.FollowPetRequest;
import com.spring.mummus.follow.service.FollowService;
import com.spring.mummus.pet.entity.Pet;
import com.spring.mummus.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController implements FollowControllerDocs {

    private final PetService petService;
    private final FollowService followService;


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 다른 강아지를 팔로우한다.
    @PostMapping()
    public void followPet(@RequestBody FollowPetRequest request) {
        Long memberId = 1L;
        Pet followingPet = followService.followPet(request, memberId);
        petService.increaseFollowerCount(followingPet);
    }


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 다른 강아지를 팔로우한다.
    @DeleteMapping()
    public void unfollowPet(@RequestBody FollowPetRequest request) {
        Long memberId = 1L;
        Pet unfollowedPet = followService.unfollowPet(request, memberId);
        petService.decreaseFollowerCount(unfollowedPet);
    }


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 내가 팔로우하는 강아지를 조회한다.
    @GetMapping("/following")
    public void getFollowingPets() {
        Long memberId = 1L;
        followService.getFollowingPets(memberId);
    }


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 내가 팔로우하는 강아지의 수를 조회한다.
    @GetMapping("/following/count")
    public void countFollowingPets() {
        Long memberId = 1L;
        followService.countFollowingPets(memberId);
    }


    // 내 강아지를 팔로우하는 강아지를 조회한다.
    @GetMapping("/followers/{petId}")
    public void getFollowerPets(@PathVariable(name = "petId") Long petId) {
        followService.getFollowerPetsByPet(petId);
    }


    // 내 강아지를 팔로우하는 강아지의 수를 조회한다.
    @GetMapping("/followers/{petId}/count")
    public void countFollowerPets(@PathVariable(name = "petId") Long petId) {
        followService.countFollowerPets(petId);
    }

}
