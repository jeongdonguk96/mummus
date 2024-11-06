package com.spring.mummus.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SingleResult<T> extends CommonResult {
    private T data;
}
