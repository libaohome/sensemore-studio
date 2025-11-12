package com.sensemore.tenant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 租户用户表
 */
@Data
@TableName("tenant_user")
@Schema(name = "TenantUser", description = "租户用户表")
public class TenantUser implements Serializable {
    @TableId(type = IdType.AUTO)
    @Schema(description = "用户ID")
    private Long id;

    @TableField
    @Schema(description = "租户业务代码", example = "TENANT2002")
    private String tenantCode;

    @TableField
    @Schema(description = "用户名", example = "admin")
    private String username;

    @TableField
    @Schema(description = "密码（加密存储）")
    private String password;

    @TableField
    @Schema(description = "邮箱", example = "admin@example.com")
    private String email;

    @TableField
    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    @TableField
    @Schema(description = "用户状态", example = "1")
    private Integer status;

    @TableField
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @TableField
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
