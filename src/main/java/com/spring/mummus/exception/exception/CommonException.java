package com.spring.mummus.exception.exception;

import com.spring.mummus.exception.code.CommonErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private final CommonErrorCode errorCode;

    public CommonException(CommonErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
