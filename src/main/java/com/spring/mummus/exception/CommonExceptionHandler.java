package com.spring.mummus.exception;

import com.spring.mummus.common.dto.CommonResult;
import com.spring.mummus.common.service.ResponseService;
import com.spring.mummus.exception.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class CommonExceptionHandler {

    private final ResponseService responseService;


    @ExceptionHandler(CommonException.class)
    public CommonResult handleException(CommonException ex) {
        return responseService.getFailResult(ex);
    }

}
