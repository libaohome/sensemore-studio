package com.sensemore.tenant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 创建用户请求 DTO
 */
@Data
@Schema(name = "CreateUserRequest", description = "创建用户参数（不包含id、createdAt、updatedAt、status等自动字段）")
public class CreateUserRequest {
    @Schema(description = "用户名", example = "user001", required = true)
    private String username;

    @Schema(description = "密码", example = "password123", required = true)
    private String password;

    @Schema(description = "邮箱", example = "user001@example.com")
    private String email;

    @Schema(description = "手机号", example = "13800138000")
    private String phone;
}

