package com.spring.mummus.service;

import com.spring.mummus.domain.dto.member.MemberSignUpDto;
import com.spring.mummus.domain.entity.Member;
import com.spring.mummus.repository.MemberRepository;
import com.spring.mummus.service.reference.MemberReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberReference memberReference;

    public void signUp (MemberSignUpDto memberSignUpDto) {
        // 이메일 중복 체크
        memberReference.checkDuplicatedEmail(memberSignUpDto.getEmail());

        // 휴대폰 번호 중복 체크
        memberReference.checkDuplicatedPhoneNumber(memberSignUpDto.getPhoneNumber());

        memberRepository.save(Member.builder()
                        .name(memberSignUpDto.getName())
                        .email(memberSignUpDto.getEmail())
                        .password(memberSignUpDto.getPassword())
                        .address(memberSignUpDto.getAddress())
                        .phoneNumber(memberSignUpDto.getPhoneNumber())
                        .build());
    }
}
