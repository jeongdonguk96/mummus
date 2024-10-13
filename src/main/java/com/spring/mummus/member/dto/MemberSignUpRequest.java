package com.spring.mummus.member.dto;

import com.spring.mummus.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원가입 요청 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpRequest {
    @Schema(description = "이메일", example = "John@gmail.com")
    private String email;
    @Schema(description = "패스워드", example = "1234")
    private String password;
    @Schema(description = "이름", example = "John Doe")
    private String name;
    @Schema(description = "주소", example = "서울시 강서구 공항동 1356-6")
    private String address;
    @Schema(description = "휴대전화 번호", example = "010-8025-6181")
    private String phoneNumber;

    public Member toEntity() {
        return Member.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .name(this.getName())
                .address(this.getAddress())
                .phoneNumber(this.getPhoneNumber())
                .build();
    }
}
