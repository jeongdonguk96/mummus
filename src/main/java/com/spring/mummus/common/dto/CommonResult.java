package com.spring.mummus.common.dto;

import com.spring.mummus.common.enums.CommonResponse;
import com.spring.mummus.exception.exception.CommonException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonResult {
    private boolean success;
    private int code;
    private String message;

    public void setSuccessResult() {
        this.success = true;
        this.code = CommonResponse.SUCCESS.getCode();
        this.message = CommonResponse.SUCCESS.getMessage();
    }

    public void setFailResult(CommonException ex) {
        this.success = false;
        this.code = ex.getErrorCode().getCode();
        this.message = ex.getErrorCode().getMessage();
    }
}
