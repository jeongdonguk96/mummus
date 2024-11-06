package com.spring.mummus.common.dto;

import com.spring.mummus.common.enums.CommonResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonResult {
    private boolean success;
    private int code;

    public void setSuccessResult() {
        this.success = true;
        this.code = CommonResponse.SUCCESS.getCode();
    }
}
