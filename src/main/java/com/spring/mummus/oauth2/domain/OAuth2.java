package com.spring.mummus.oauth2.domain;

public interface OAuth2 {
    String getProvider(); // 플랫폼명
    String getProviderId(); // 플랫폼 자체에서 사용자를 저장하는 id 값 -> 대부분 123456456489789asddas와 같은 난수
    String getEmail(); // 플랫폼 가입 시 제출한 email
    String getName();
}
