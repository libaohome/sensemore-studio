package com.sensemore.tenant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 创建租户请求 DTO
 */
@Data
@Schema(name = "CreateTenantRequest", description = "创建租户参数（不包含id、createdAt、updatedAt、status等自动字段）")
public class CreateTenantRequest {
    @Schema(description = "租户业务代码", example = "TENANT2002")
    private String tenantCode;

    @Schema(description = "租户名称", example = "演示租户")
    private String tenantName;

    @Schema(description = "联系人", example = "张三")
    private String contactPerson;

    @Schema(description = "联系电话", example = "13800138000")
    private String contactPhone;

    @Schema(description = "联系邮箱", example = "contact@example.com")
    private String contactEmail;

    @Schema(description = "描述", example = "这是一个演示租户")
    private String description;
}
