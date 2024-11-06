package com.spring.mummus.exception.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {
    private int code;
    private String message;
    private HttpStatus httpStatus;
}
