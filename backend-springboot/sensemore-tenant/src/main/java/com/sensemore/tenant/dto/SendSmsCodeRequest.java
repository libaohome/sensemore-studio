package com.sensemore.tenant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 发送短信验证码请求 DTO
 */
@Data
@Schema(name = "SendSmsCodeRequest", description = "发送短信验证码参数")
public class SendSmsCodeRequest {
    @Schema(description = "租户业务代码", example = "TENANT1001", required = true)
    private String tenantCode;

    @Schema(description = "手机号", example = "13800138000", required = true)
    private String phone;
}

