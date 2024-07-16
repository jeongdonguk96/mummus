package com.spring.mummus.controller;

import com.spring.mummus.domain.dto.member.MemberSignUpDto;
import com.spring.mummus.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public void signUp (@RequestBody MemberSignUpDto memberSignUpDto) {
        memberService.signUp(memberSignUpDto);
    }
}
