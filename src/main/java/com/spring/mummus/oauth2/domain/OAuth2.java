package com.spring.mummus.oauth2.domain;

import com.spring.mummus.member.entity.Member;

public interface OAuth2 {

    String getProvider(); // 플랫폼명
    String getEmail(); // 플랫폼 가입 시 제출한 email
    String getName();


    Member from(OAuth2 oAuth2);
}
