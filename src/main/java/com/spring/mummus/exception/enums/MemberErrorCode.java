package com.spring.mummus.exception.enums;

import com.spring.mummus.exception.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, 400, "유저를 찾을 수 없습니다."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, 400, "이미 가입된 이메일입니다."),
    DUPLICATED_PHONE_NUMBER(HttpStatus.BAD_REQUEST, 400, "이미 가입된 휴대폰 번호입니다.")
    ;

    private final HttpStatus httpStatus;
    private final Integer errorCode;
    private final String message;
}
