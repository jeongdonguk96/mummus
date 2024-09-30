//package com.spring.mummus.member.service;
//
//import com.spring.mummus.member.domain.entity.Member;
//import com.spring.mummus.exception.exception.MemberException;
//import com.spring.mummus.member.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import static com.spring.mummus.exception.enums.MemberErrorCode.*;
//
//@Service
//@RequiredArgsConstructor
//public class MemberReference {
//    private final MemberRepository memberRepository;
//
//    public void checkDuplicatedEmail(String email) {
//        if (memberRepository.existsByEmail(email)) {
//            throw new MemberException(DUPLICATED_EMAIL);
//        }
//    }
//
//    public void checkDuplicatedPhoneNumber(String phoneNumber) {
//        if (memberRepository.existsByPhoneNumber(phoneNumber)) {
//            throw new MemberException(DUPLICATED_PHONE_NUMBER);
//        }
//    }
//
//    public Member findById(Long id) {
//        return memberRepository.findById(id).orElseThrow(() -> new MemberException(USER_NOT_FOUND));
//    }
//}
