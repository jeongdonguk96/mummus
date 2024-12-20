package com.spring.mummus.member.service;

import com.spring.mummus.exception.code.MemberErrorCode;
import com.spring.mummus.exception.exception.CommonException;
import com.spring.mummus.member.dto.MemberSignUpRequest;
import com.spring.mummus.member.entity.Member;
import com.spring.mummus.member.repository.MemberRepository;
import com.spring.mummus.oauth2.domain.OAuth2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.spring.mummus.exception.code.MemberErrorCode.DUPLICATED_EMAIL;
import static com.spring.mummus.exception.code.MemberErrorCode.DUPLICATED_PHONE_NUMBER;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    // 회원가입을 진행한다.
    @Transactional
    public Member signUp(MemberSignUpRequest memberSignUpRequest) {
        checkDuplicatedEmail(memberSignUpRequest.getEmail());
        checkDuplicatedPhoneNumber(memberSignUpRequest.getPhoneNumber());

        return memberRepository.save(memberSignUpRequest.toEntity());
    }


    // OAuth 회원의 회원가입을 진행한다.
    @Transactional
    public Member singUp(OAuth2 oAuth2) {
        Member newMember = oAuth2.from(oAuth2);
        return memberRepository.save(newMember);
    }


    // 회원 존재 여부를 확인한다.
    @Transactional(readOnly = true)
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                ()-> new CommonException(MemberErrorCode.USER_NOT_FOUND));
    }


    // 중복된 이메일이 있는지 확인한다.
    private void checkDuplicatedEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new CommonException(DUPLICATED_EMAIL);
        }
    }


    // 중복된 전화번호가 있는지 확인한다.
    private void checkDuplicatedPhoneNumber(String phoneNumber) {
        if (memberRepository.existsByPhoneNumber(phoneNumber)) {
            throw new CommonException(DUPLICATED_PHONE_NUMBER);
        }
    }
}
