package com.sensemore.tenant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户响应 DTO
 * 用于返回给前端，不包含敏感信息（如密码）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserResponse", description = "用户响应信息（不包含密码等敏感字段）")
public class UserResponse {
    
    @Schema(description = "用户ID", example = "1")
    private Long id;

    @Schema(description = "租户业务代码", example = "TENANT1001")
    private String tenantCode;

    @Schema(description = "用户名", example = "user001")
    private String username;

    @Schema(description = "邮箱", example = "user001@example.com")
    private String email;

    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    @Schema(description = "用户状态: 0-禁用, 1-启用", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}

