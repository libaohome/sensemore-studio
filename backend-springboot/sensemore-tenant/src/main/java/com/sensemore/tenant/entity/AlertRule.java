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
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 告警规则实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("alert_rule")
@Schema(name = "AlertRule", description = "告警规则实体")
public class AlertRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 告警规则ID（内部使用，用于索引）
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "告警规则ID（内部ID，仅用于索引）", example = "1")
    private Long id;

    /**
     * 规则业务代码（对外使用）
     */
    @TableField
    @Schema(description = "规则业务代码（对外使用）", example = "RULE1001")
    private String ruleCode;

    /**
     * 租户业务代码（替代外键）
     */
    @TableField
    @Schema(description = "租户业务代码", example = "TENANT1001")
    private String tenantCode;

    /**
     * 规则名称
     */
    @TableField
    private String ruleName;

    /**
     * 规则类型: quota_exceeded, cost_exceeded, error_rate_high
     */
    @TableField
    private String ruleType;

    /**
     * 触发条件值
     */
    @TableField
    private BigDecimal conditionValue;

    /**
     * 告警邮箱（逗号分隔）
     */
    @TableField
    private String alertEmail;

    /**
     * 告警电话（逗号分隔）
     */
    @TableField
    private String alertPhone;

    /**
     * 状态: 0-禁用, 1-启用
     */
    @TableField
    private Integer status;

    /**
     * 创建时间
     */
    @TableField
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField
    private LocalDateTime updatedAt;

}
