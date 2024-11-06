package com.spring.mummus.follow.controller;

import com.spring.mummus.common.dto.CommonResult;
import com.spring.mummus.follow.dto.FollowPetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "팔로우 API")
public interface FollowControllerDocs {

    @Operation(summary = "팔로우", description = "다른 강아지를 팔로우한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public CommonResult followPet(@RequestBody FollowPetRequest request);
    
    @Operation(summary = "언팔로우", description = "다른 강아지를 언팔로우한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public CommonResult unfollowPet(@RequestBody FollowPetRequest request);

    @Operation(summary = "팔로우 조회", description = "내가 팔로우하는 강아지를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public CommonResult getFollowingPets();

    @Operation(summary = "팔로우 수 조회", description = "내가 팔로우하는 강아지의 수를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public CommonResult countFollowingPets();

    @Operation(summary = "팔로워 조회", description = "내 강아지를 팔로우하는 강아지를 조회한다.")
    @Parameter(name = "petId", description = "내 강아지의 ID", example = "1", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public CommonResult getFollowerPets(@PathVariable(name = "petId") Long petId);

    @Operation(summary = "팔로워 수 조회", description = "내 강아지를 팔로우하는 강아지의 수를 조회한다.")
    @Parameter(name = "petId", description = "내 강아지의 ID", example = "1", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public CommonResult countFollowerPets(@PathVariable(name = "petId") Long petId);

}
