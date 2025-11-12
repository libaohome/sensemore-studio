package com.sensemore.tenant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 修改密码请求 DTO
 */
@Data
@Schema(name = "ChangePasswordRequest", description = "修改密码参数")
public class ChangePasswordRequest {
    @Schema(description = "租户业务代码", example = "TENANT1001", required = true)
    private String tenantCode;

    @Schema(description = "用户名", example = "user001", required = true)
    private String username;

    @Schema(description = "旧密码", example = "oldPassword123", required = true)
    private String oldPassword;

    @Schema(description = "新密码", example = "newPassword123", required = true)
    private String newPassword;
}

