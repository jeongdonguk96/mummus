package com.spring.mummus.exception.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class PetExceptionResponse {
    private HttpStatus httpStatus;
    private Integer errorCode;
    private String errorMessage;
}
