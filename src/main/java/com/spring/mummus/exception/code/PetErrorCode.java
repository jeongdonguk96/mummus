package com.spring.mummus.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PetErrorCode implements CommonErrorCode {
    PET_NOT_FOUND(400, "해당 반려견을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    DUPLICATED_PET(400, "이미 등록한 반려견입니다.", HttpStatus.BAD_REQUEST),
    MEMBER_HAS_NO_PETS(400, "보유한 반려견이 없습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
