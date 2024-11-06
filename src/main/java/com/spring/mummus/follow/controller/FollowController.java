package com.spring.mummus.follow.controller;

import com.spring.mummus.common.dto.CommonResult;
import com.spring.mummus.common.service.ResponseService;
import com.spring.mummus.follow.dto.FollowPetRequest;
import com.spring.mummus.follow.service.FollowService;
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

    private final FollowService followService;
    private final ResponseService responseService;


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 다른 강아지를 팔로우한다.
    @PostMapping()
    public CommonResult followPet(@RequestBody FollowPetRequest request) {
        Long memberId = 1L;
        followService.followPet(request, memberId);
        return responseService.getSuccessResult();
    }


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 다른 강아지를 언팔로우한다.
    @DeleteMapping()
    public CommonResult unfollowPet(@RequestBody FollowPetRequest request) {
        Long memberId = 1L;
        followService.unfollowPet(request, memberId);
        return responseService.getSuccessResult();
    }


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 내가 팔로우하는 강아지를 조회한다.
    @GetMapping("/following")
    public CommonResult getFollowingPets() {
        Long memberId = 1L;
        return responseService.getListResult(followService.getFollowingPets(memberId));
    }


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 내가 팔로우하는 강아지의 수를 조회한다.
    @GetMapping("/following/count")
    public CommonResult countFollowingPets() {
        Long memberId = 1L;
        return responseService.getSingleResult(followService.countFollowingPets(memberId));
    }


    // 내 강아지를 팔로우하는 강아지를 조회한다.
    @GetMapping("/followers/{petId}")
    public CommonResult getFollowerPets(@PathVariable(name = "petId") Long petId) {
        return responseService.getListResult(followService.getFollowerPetsByPet(petId));
    }


    // 내 강아지를 팔로우하는 강아지의 수를 조회한다.
    @GetMapping("/followers/{petId}/count")
    public CommonResult countFollowerPets(@PathVariable(name = "petId") Long petId) {
        return responseService.getSingleResult(followService.countFollowerPets(petId));
    }

}
