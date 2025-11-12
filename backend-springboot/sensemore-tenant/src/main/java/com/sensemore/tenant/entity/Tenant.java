package com.sensemore.tenant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 租户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tenant")
@Schema(name = "Tenant", description = "租户实体")
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户ID
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "租户ID", example = "1")
    private Long id;

    /**
     * 租户代码
     */
    @TableField
    @Schema(description = "租户代码（唯一）", example = "TENANT001")
    private String tenantCode;

    /**
     * 租户名称
     */
    @TableField
    @Schema(description = "租户名称", example = "演示租户")
    private String tenantName;

    /**
     * 联系人
     */
    @TableField
    @Schema(description = "联系人", example = "张三")
    private String contactPerson;

    /**
     * 联系电话
     */
    @TableField
    @Schema(description = "联系电话", example = "13800138000")
    private String contactPhone;

    /**
     * 联系邮箱
     */
    @TableField
    @Schema(description = "联系邮箱", example = "contact@example.com")
    private String contactEmail;

    /**
     * 租户状态: 0-禁用, 1-启用
     */
    @TableField
    @Schema(description = "租户状态: 0-禁用, 1-启用", example = "1")
    private Integer status;

    /**
     * 描述
     */
    @TableField
    @Schema(description = "租户描述", example = "这是一个演示租户")
    private String description;

    /**
     * 创建时间
     */
    @TableField
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
