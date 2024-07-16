package com.spring.mummus.exception;

import com.spring.mummus.exception.exception.MemberException;
import com.spring.mummus.exception.exception.PetException;
import com.spring.mummus.exception.response.MemberExceptionResponse;
import com.spring.mummus.exception.response.PetExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MummusExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<MemberExceptionResponse> handleMemberException(MemberException e) {
        MemberExceptionResponse memberExceptionResponse = MemberExceptionResponse.builder()
                .httpStatus(e.getErrorCode().getHttpStatus())
                .errorCode(e.getErrorCode().getErrorCode())
                .errorMessage(e.getMessage())
                .build();

        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(memberExceptionResponse);
    }

    @ExceptionHandler(PetException.class)
    public ResponseEntity<PetExceptionResponse> handlePetException(PetException e) {
        PetExceptionResponse petExceptionResponse = PetExceptionResponse.builder()
                .httpStatus(e.getErrorCode().getHttpStatus())
                .errorCode(e.getErrorCode().getErrorCode())
                .errorMessage(e.getMessage())
                .build();

        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(petExceptionResponse);
    }
}
