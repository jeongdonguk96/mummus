package com.spring.mummus.member.service;

import com.spring.mummus.member.domain.dto.MemberSignUpRequest;
import com.spring.mummus.member.domain.entity.Member;
import com.spring.mummus.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberReference memberReference;

    @Transactional
    public void signUp (MemberSignUpRequest memberSignUpRequest) {
        // 이메일 중복 체크
        memberReference.checkDuplicatedEmail(memberSignUpRequest.getEmail());

        // 휴대폰 번호 중복 체크
        memberReference.checkDuplicatedPhoneNumber(memberSignUpRequest.getPhoneNumber());

        memberRepository.save(memberSignUpRequest.toEntity());
    }


    @Transactional
    public Member singUp(String nickname) {
        Member newMember = Member.builder()
                .name(nickname)
                .build();

        return memberRepository.save(newMember);
    }

}
