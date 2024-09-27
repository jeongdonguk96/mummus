package com.spring.mummus.follow.controller;

import com.spring.mummus.follow.domain.dto.FollowPetRequest;
import com.spring.mummus.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;


    // 다른 강아지를 팔로우한다.
    @PostMapping()
    public void followPet(FollowPetRequest request) {
        followService.followPet(request);
    }


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 팔로잉하는 강아지를 조회한다.
    @GetMapping("/followings")
    public void getFollowingPets(Long id) {
        followService.getFollowingPets(id);
    }


    // 팔로잉하는 강아지의 수를 조회한다.
    @GetMapping("/followings/count")
    public void countFollowingPets(Long id) {
        followService.countFollowingPets(id);
    }


    // 팔로워 강아지를 조회한다.
    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    @GetMapping("/followers")
    public void getFollowerPets(Long id) {
        followService.getFollowerPets(id);
    }


    // 팔로워 강아지의 수를 조회한다.
    @GetMapping("/followers/count")
    public void countFollowerPets(Long id) {
        followService.countFollowerPets(id);
    }

}
