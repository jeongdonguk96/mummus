package com.spring.mummus.exception.enums;

import com.spring.mummus.exception.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PetErrorCode implements ErrorCode {
    DUPLICATED_PET(HttpStatus.BAD_REQUEST, 400, "이미 등록한 반려견입니다.")
    ;

    private final HttpStatus httpStatus;
    private final Integer errorCode;
    private final String message;
}
