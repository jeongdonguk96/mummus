package com.spring.mummus.member.controller;

import com.spring.mummus.member.dto.MemberSignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "사용자 API")
public interface MemberControllerDocs {

    @Operation(summary = "회원가입", description = "회원가입을 진행한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public void signUp (@RequestBody MemberSignUpRequest memberSignUpRequest);

}
