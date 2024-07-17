package com.spring.mummus.member.domain.dto;

import com.spring.mummus.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSignUpRequest {
    private String email;
    private String password;
    private String name;
    private String address;
    private String phoneNumber;

    public Member toEntity() {
        return Member.builder()
                .name(this.getName())
                .email(this.getEmail())
                .password(this.getPassword())
                .address(this.getAddress())
                .phoneNumber(this.getPhoneNumber())
                .build();
    }
}
