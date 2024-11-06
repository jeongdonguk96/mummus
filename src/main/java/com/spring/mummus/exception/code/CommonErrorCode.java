package com.spring.mummus.exception.code;

import org.springframework.http.HttpStatus;

public interface CommonErrorCode {
    int getCode();
    String getMessage();
    HttpStatus getHttpStatus();
}
