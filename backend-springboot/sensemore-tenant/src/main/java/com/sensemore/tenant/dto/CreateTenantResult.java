package com.sensemore.tenant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 创建租户结果
 */
@Data
@Schema(name = "CreateTenantResult", description = "创建租户后的默认账号信息")
public class CreateTenantResult {

    @Schema(description = "租户业务代码", example = "TENANT2002")
    private String tenantCode;

    @Schema(description = "租户名称", example = "演示租户")
    private String tenantName;

    @Schema(description = "默认管理员用户名", example = "admin")
    private String defaultUsername;

    @Schema(description = "默认管理员密码", example = "演示租户contact@example.com")
    private String defaultPassword;
}

