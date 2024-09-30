package com.spring.mummus.member.service;

import com.spring.mummus.member.dto.MemberSignUpRequest;
import com.spring.mummus.member.entity.Member;
import com.spring.mummus.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    @Test
    @DisplayName("회원가입이 정상적으로 성공한다.")
    void signUpTest() {
        // given
        MemberSignUpRequest request = new MemberSignUpRequest("aaa@aaa.com", "1234", "김데데", "부천시 오정구", "010-1234-5678");

        // when
        memberService.signUp(request);
        List<Member> members = memberRepository.findAll();
        Member member = members.get(0);

        // then
        assertThat(member.getEmail().equals("aaa@aaa.com"));
        assertThat(member.getPassword().equals("1234"));
        assertThat(member.getName().equals("김데데"));
        assertThat(member.getAddress().equals("부천시 오정구"));
        assertThat(member.getPhoneNumber().equals("010-1234-5678"));
    }



}