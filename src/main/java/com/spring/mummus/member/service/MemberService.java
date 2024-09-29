package com.spring.mummus.member.service;

import com.spring.mummus.exception.enums.MemberErrorCode;
import com.spring.mummus.exception.exception.MemberException;
import com.spring.mummus.member.dto.MemberSignUpRequest;
import com.spring.mummus.member.entity.Member;
import com.spring.mummus.member.repository.MemberRepository;
import com.spring.mummus.oauth2.domain.OAuth2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.spring.mummus.exception.enums.MemberErrorCode.DUPLICATED_EMAIL;
import static com.spring.mummus.exception.enums.MemberErrorCode.DUPLICATED_PHONE_NUMBER;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
//    private final MemberReference memberReference;


    @Transactional
    public void signUp (MemberSignUpRequest memberSignUpRequest) {
        // 이메일 중복 체크
        checkDuplicatedEmail(memberSignUpRequest.getEmail());

        // 휴대폰 번호 중복 체크
        checkDuplicatedPhoneNumber(memberSignUpRequest.getPhoneNumber());

        memberRepository.save(memberSignUpRequest.toEntity());
    }


    @Transactional
    public Member singUp(OAuth2 oAuth2) {
        Member newMember = Member.builder()
                .name(oAuth2.getName())
                .provider(oAuth2.getProvider())
                .build();

        return memberRepository.save(newMember);
    }


    private void checkDuplicatedEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberException(DUPLICATED_EMAIL);
        }
    }


    private void checkDuplicatedPhoneNumber(String phoneNumber) {
        if (memberRepository.existsByPhoneNumber(phoneNumber)) {
            throw new MemberException(DUPLICATED_PHONE_NUMBER);
        }
    }


    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                ()-> new MemberException(MemberErrorCode.USER_NOT_FOUND));
    }

}
