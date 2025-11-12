package com.sensemore.tenant.dto;

import lombok.Data;

/**
 * 注销结果（已废弃，统一使用ApiResponse）
 * @deprecated 使用 ApiResponse 替代
 */
@Data
@Deprecated
public class LogoutResult<T> {
    private Integer code;
    private String message;
    private T data;
}
