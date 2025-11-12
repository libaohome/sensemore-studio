package com.sensemore.tenant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 找回密码请求 DTO
 */
@Data
@Schema(name = "ResetPasswordRequest", description = "根据手机号短信验证码找回密码参数")
public class ResetPasswordRequest {
    @Schema(description = "租户业务代码", example = "TENANT1001", required = true)
    private String tenantCode;

    @Schema(description = "手机号", example = "13800138000", required = true)
    private String phone;

    @Schema(description = "短信验证码", example = "123456", required = true)
    private String smsCode;

    @Schema(description = "新密码", example = "newPassword123", required = true)
    private String newPassword;
}

