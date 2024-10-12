package com.spring.mummus.member.dto;

import com.spring.mummus.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpRequest {
    private String email;
    private String password;
    private String name;
    private String address;
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
