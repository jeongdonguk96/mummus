package com.spring.mummus.pet.controller;

import com.spring.mummus.common.dto.CommonResult;
import com.spring.mummus.pet.dto.CreatePetRequest;
import com.spring.mummus.pet.dto.DeletePetProfileImageRequest;
import com.spring.mummus.pet.enums.Gender;
import com.spring.mummus.pet.enums.PetType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "강아지 API")
interface PetControllerDocs {


    @Operation(summary = "강아지 등록", description = "강아지를 등록한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    CommonResult createPet(@RequestBody CreatePetRequest request);

    @Operation(summary = "강아지 조회", description = "다른 강아지를 조회한다.")
    @Parameter(name = "petId", description = "강아지의 Id", example = "1", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    CommonResult getPet(@PathVariable(name = "petId") Long petId);

    @Operation(summary = "내 강아지 조회", description = "내 강아지들을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    CommonResult getMyPets();

    @Operation(summary = "강아지 이름 변경", description = "강아지의 이름을 변경한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    CommonResult modifyPetName(@PathVariable(name = "petId") Long petId, @RequestBody String name);
    
    @Operation(summary = "강아지 나이 변경", description = "강아지의 나이를 변경한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    CommonResult modifyPetAge(@PathVariable(name = "petId") Long petId, @RequestBody int age);

    @Operation(summary = "강아지 생일 변경", description = "강아지의 생일을 변경한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    CommonResult modifyPetBirth(@PathVariable(name = "petId") Long petId, @RequestBody String birth);

    @Operation(summary = "강아지 성별 변경", description = "강아지의 성별을 변경한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    CommonResult modifyPetGender(@PathVariable(name = "petId") Long petId, @RequestBody Gender gender);

    @Operation(summary = "강아지 견종 변경", description = "강아지의 견종을 변경한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    CommonResult modifyPetType(@PathVariable(name = "petId") Long petId, @RequestBody PetType petType);

    @Operation(summary = "강아지 프로필 사진 등록", description = "강아지의 프로필 사진을 등록한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    CommonResult createPetProfileImage(@RequestPart(name = "file") MultipartFile file) throws IOException;

    @Operation(summary = "강아지 프로필 사진 변경", description = "강아지의 프로필 사진을 변경한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    CommonResult modifyPetProfileImage(@PathVariable(name = "petId") Long petId, @RequestPart(name = "file") MultipartFile file) throws IOException;

    @Operation(summary = "강아지 프로필 사진 롤백", description = "임시 등록한 강아지의 프로필 사진을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    CommonResult rollbackPetProfileImage(@RequestBody DeletePetProfileImageRequest request);

    @Operation(summary = "강아지 프로필 사진 삭제", description = "등록한 강아지의 프로필 사진을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    CommonResult deletePetProfileImage(@PathVariable(name = "petId") Long petId, @RequestBody DeletePetProfileImageRequest request);

    @Operation(summary = "강아지 삭제", description = "강아지를 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    CommonResult deletePet(@PathVariable(name = "petId") Long petId);

}
