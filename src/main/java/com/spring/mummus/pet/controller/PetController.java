package com.spring.mummus.pet.controller;

import com.spring.mummus.common.dto.CommonResult;
import com.spring.mummus.common.service.ResponseService;
import com.spring.mummus.pet.dto.CreatePetRequest;
import com.spring.mummus.pet.dto.DeletePetProfileImageRequest;
import com.spring.mummus.pet.enums.Gender;
import com.spring.mummus.pet.enums.PetType;
import com.spring.mummus.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController implements PetControllerDocs {

    private final PetService petService;
    private final ResponseService responseService;


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 강아지를 등록한다.
    @PostMapping()
    public CommonResult createPet(@RequestBody CreatePetRequest request) {
        Long memberId = 1L;
        petService.createPet(request, memberId);
        return responseService.getSuccessResult();
    }


    // 다른 강아지를 조회한다.
    @GetMapping("/{petId}")
    public CommonResult getPet(@PathVariable(name = "petId") Long petId) {
        return responseService.getSingleResult(petService.findById(petId));
    }


    // TODO: 추후 시큐리티 컨텍스트에서 id값 꺼내오기
    // 사용자의 강아지를 조회한다.
    @GetMapping("/my-pets")
    public CommonResult getMyPets() {
        Long memberId = 1L;
        return responseService.getListResult(petService.getMyPets(memberId));
    }


    // 강아지의 이름을 변경한다.
    @PutMapping("/{petId}/name")
    public CommonResult modifyPetName(
            @PathVariable(name = "petId") Long petId,
            @RequestBody String name
    ) {
        petService.modifyPetName(petId, name);
        return responseService.getSuccessResult();
    }


    // 강아지의 나이를을 변경한다.
    @PutMapping("/{petId}/age")
    public CommonResult modifyPetAge(
            @PathVariable(name = "petId") Long petId,
            @RequestBody int age
    ) {
        petService.modifyPetAge(petId, age);
        return responseService.getSuccessResult();
    }


    // 강아지의 생일을 변경한다.
    @PutMapping("/{petId}/birth")
    public CommonResult modifyPetBirth(
            @PathVariable(name = "petId") Long petId,
            @RequestBody String birth
    ) {
        petService.modifyPetBirth(petId, birth);
        return responseService.getSuccessResult();
    }


    // 강아지의 성별을 변경한다.
    @PutMapping("/{petId}/gender")
    public CommonResult modifyPetGender(
            @PathVariable(name = "petId") Long petId,
            @RequestBody Gender gender
    ) {
        petService.modifyPetGender(petId, gender);
        return responseService.getSuccessResult();
    }


    // 강아지의 견종을 변경한다.
    @PutMapping("/{petId}/type")
    public CommonResult modifyPetType(
            @PathVariable(name = "petId") Long petId,
            @RequestBody PetType petType
    ) {
        petService.modifyPetType(petId, petType);
        return responseService.getSuccessResult();
    }


    // 강아지의 프로필 사진을 등록한다.
    @PostMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult createPetProfileImage(@RequestPart(name = "file") MultipartFile file) throws IOException {
        Long memberId = 1L;
        return responseService.getSingleResult(petService.createPetProfileImage(file, memberId));
    }


    // 강아지의 프로필 사진을 변경한다.
    @PutMapping(value = "/profile-image/{petId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult modifyPetProfileImage(
            @PathVariable(name = "petId") Long petId,
            @RequestPart(name = "file") MultipartFile file
    ) throws IOException {
        Long memberId = 1L;
        petService.modifyPetProfileImage(petId, file, memberId);
        return responseService.getSuccessResult();
    }


    // 임시 등록한 강아지의 프로필 이미지를 삭제한다.
    @DeleteMapping("/profile-image")
    public CommonResult rollbackPetProfileImage(@RequestBody DeletePetProfileImageRequest request) {
        petService.rollbackPetProfileImage(request);
        return responseService.getSuccessResult();
    }


    // 강아지의 프로필 사진을 삭제한다.
    @DeleteMapping("/profile-image/{petId}")
    public CommonResult deletePetProfileImage(
            @PathVariable(name = "petId") Long petId,
            @RequestBody DeletePetProfileImageRequest request
    ) {
        petService.deletePetProfileImage(petId, request);
        return responseService.getSuccessResult();
    }


    // 강아지를 삭제한다.
    @DeleteMapping("/{petId}")
    public CommonResult deletePet(@PathVariable(name = "petId") Long petId) {
        petService.deletePet(petId);
        return responseService.getSuccessResult();
    }

}
