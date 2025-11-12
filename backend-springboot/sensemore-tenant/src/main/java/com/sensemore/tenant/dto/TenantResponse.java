package com.sensemore.tenant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 租户响应 DTO
 * 用于返回给前端
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "TenantResponse", description = "租户响应信息")
public class TenantResponse {
    
    @Schema(description = "租户ID", example = "1")
    private Long id;

    @Schema(description = "租户代码（唯一）", example = "TENANT001")
    private String tenantCode;

    @Schema(description = "租户名称", example = "演示租户")
    private String tenantName;

    @Schema(description = "联系人", example = "张三")
    private String contactPerson;

    @Schema(description = "联系电话", example = "13800138000")
    private String contactPhone;

    @Schema(description = "联系邮箱", example = "contact@example.com")
    private String contactEmail;

    @Schema(description = "租户状态: 0-禁用, 1-启用", example = "1")
    private Integer status;

    @Schema(description = "租户描述", example = "这是一个演示租户")
    private String description;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}

