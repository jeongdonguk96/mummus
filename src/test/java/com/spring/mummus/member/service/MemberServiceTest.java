package com.spring.mummus.member.service;

import com.spring.mummus.AbstractTest;
import com.spring.mummus.exception.exception.MemberException;
import com.spring.mummus.member.dto.MemberSignUpRequest;
import com.spring.mummus.member.entity.Member;
import com.spring.mummus.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.spring.mummus.exception.enums.MemberErrorCode.DUPLICATED_EMAIL;
import static com.spring.mummus.exception.enums.MemberErrorCode.DUPLICATED_PHONE_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest extends AbstractTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

  
    @Test
    @DisplayName("회원가입이 성공한다.")
    void signUpTest() {
        //given
        MemberSignUpRequest request = new MemberSignUpRequest("test@naver.com", "password", "testName", "testAddress", "testPhoneNumber");

        //when
        memberService.signUp(request);
        Member member = memberRepository.findById(1L).get();

        //then
        assertThat(member.getEmail()).isEqualTo("test@naver.com");
        assertThat(member.getName()).isEqualTo("testName");
        assertThat(member.getPassword()).isEqualTo("password");
        assertThat(member.getAddress()).isEqualTo("testAddress");
        assertThat(member.getPhoneNumber()).isEqualTo("testPhoneNumber");
    }


    @Test
    @DisplayName("중복된 이메일을 확인한다.")
    void checkDuplicatedEmailTest() {
        //given
        MemberSignUpRequest request1 = new MemberSignUpRequest("test@naver.com", "password1", "testName1", "testAddress1", "testPhoneNumber1");
        MemberSignUpRequest request2 = new MemberSignUpRequest("test@naver.com", "password2", "testName2", "testAddress2", "testPhoneNumber2");

        //when
        memberService.signUp(request1);
        MemberException memberException = assertThrows(MemberException.class, () -> {
            memberService.signUp(request2);
        });

        //then
        assertEquals(DUPLICATED_EMAIL, memberException.getErrorCode());
    }


    @Test
    @DisplayName("중복된 전화번호를 확인한다.")
    void checkDuplicatedPhoneNumberTest() {
        //given
        MemberSignUpRequest request1 = new MemberSignUpRequest("test@naver.com1", "password1", "testName1", "testAddress1", "testPhoneNumber");
        MemberSignUpRequest request2 = new MemberSignUpRequest("test@naver.com2", "password2", "testName2", "testAddress2", "testPhoneNumber");

        //when
        memberService.signUp(request1);
        MemberException memberException = assertThrows(MemberException.class, () -> {
            memberService.signUp(request2);
        });

        //then
        assertEquals(DUPLICATED_PHONE_NUMBER, memberException.getErrorCode());
    }


    @Test
    @DisplayName("회원 존재 여부가 확인된다.")
    void findByIdTest() {
        //given
        MemberSignUpRequest request = new MemberSignUpRequest("test@naver.com1", "password1", "testName1", "testAddress1", "testPhoneNumber");

        //when
        memberService.signUp(request);
        Member member = memberRepository.findByEmail("test@naver.com1");
        Member targetMember = memberService.findById(member.getId());

        //then
        assertThat(member.getId()).isEqualTo(targetMember.getId());
        assertThat(member.getEmail()).isEqualTo(targetMember.getEmail());
        assertThat(member.getName()).isEqualTo(targetMember.getName());
    }


}