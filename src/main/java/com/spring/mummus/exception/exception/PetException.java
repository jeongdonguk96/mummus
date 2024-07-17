package com.spring.mummus.exception.exception;

import lombok.Getter;

@Getter
public class PetException extends RuntimeException {
    private final ErrorCode errorCode;


    public PetException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
