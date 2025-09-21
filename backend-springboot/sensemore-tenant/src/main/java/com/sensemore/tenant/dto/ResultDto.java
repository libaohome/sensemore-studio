package com.sensemore.tenant.dto;

import lombok.Data;

@Data
public abstract class ResultDto<T> {
    private Integer code;
    private String message;
    private T data;
}
