package com.spring.mummus.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements CommonErrorCode {
    USER_NOT_FOUND(400, "유저를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    DUPLICATED_EMAIL(400, "이미 가입된 이메일입니다.", HttpStatus.BAD_REQUEST),
    DUPLICATED_PHONE_NUMBER(400, "이미 가입된 휴대폰 번호입니다.", HttpStatus.BAD_REQUEST)
    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
