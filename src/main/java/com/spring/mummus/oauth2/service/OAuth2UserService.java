package com.spring.mummus.oauth2.service;

import com.spring.mummus.member.domain.entity.Member;
import com.spring.mummus.member.repository.MemberRepository;
import com.spring.mummus.member.service.MemberService;
import com.spring.mummus.oauth2.domain.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest = {}", userRequest);
        log.info("userRequest getAccessToken = {}", userRequest.getAccessToken());
        log.info("userRequest getClientRegistration = {}", userRequest.getClientRegistration());
        log.info("userRequest getAdditionalParameters = {}", userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User = " + oAuth2User);
        log.info("oAuth2User getAttributes = " + oAuth2User.getAttributes());
        log.info("oAuth2User getName = " + oAuth2User.getName());
        log.info("oAuth2User getAuthorities = " + oAuth2User.getAuthorities());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        Member member = null;
        switch (provider) {
            case "kakao" -> member = memberService.singUp("d");
            case "apple" -> member = memberService.singUp("d");
        }
        log.info("member = " + member);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        return new PrincipalDetails(member, attributes);
    }

}
