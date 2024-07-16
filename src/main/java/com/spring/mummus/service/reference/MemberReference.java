package com.spring.mummus.service.reference;

import com.spring.mummus.domain.entity.Member;
import com.spring.mummus.exception.exception.MemberException;
import com.spring.mummus.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.spring.mummus.exception.enums.MemberErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberReference {
    private final MemberRepository memberRepository;

    public void checkDuplicatedEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberException(DUPLICATED_EMAIL);
        }
    }

    public void checkDuplicatedPhoneNumber(String phoneNumber) {
        if (memberRepository.existsByPhoneNumber(phoneNumber)) {
            throw new MemberException(DUPLICATED_PHONE_NUMBER);
        }
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberException(USER_NOT_FOUND));
    }
}
