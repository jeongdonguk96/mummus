package com.spring.mummus.exception.enums;

import com.spring.mummus.exception.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PetErrorCode implements ErrorCode {
    PET_NOT_FOUND(HttpStatus.BAD_REQUEST, 400, "해당 반려견을 찾을 수 없습니다."),
    DUPLICATED_PET(HttpStatus.BAD_REQUEST, 400, "이미 등록한 반려견입니다."),
    MEMBER_HAS_NO_PETS(HttpStatus.BAD_REQUEST, 400, "보유한 반려견이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final Integer errorCode;
    private final String message;
}
