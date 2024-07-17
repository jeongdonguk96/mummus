package com.spring.mummus.exception.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getHttpStatus();
    Integer getErrorCode();
    String getMessage();
}
