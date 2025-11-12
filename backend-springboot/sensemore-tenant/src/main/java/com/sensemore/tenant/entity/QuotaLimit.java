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
 * 配额限制实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("quota_limit")
@Schema(name = "QuotaLimit", description = "配额限制实体")
public class QuotaLimit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配额ID（内部使用，用于索引）
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "配额ID（内部ID，仅用于索引）", example = "1")
    private Long id;

    /**
     * 配额业务代码（对外使用）
     */
    @TableField
    @Schema(description = "配额业务代码（对外使用）", example = "QUOTA1001")
    private String quotaCode;

    /**
     * 租户业务代码（替代外键）
     */
    @TableField
    @Schema(description = "租户业务代码", example = "TENANT1001")
    private String tenantCode;

    /**
     * 每日请求限制 (-1 = 无限)
     */
    @TableField
    private Long dailyRequestLimit;

    /**
     * 每日token限制 (-1 = 无限)
     */
    @TableField
    private Long dailyTokenLimit;

    /**
     * 每月费用限制 (-1 = 无限)
     */
    @TableField
    private BigDecimal monthlyCostLimit;

    /**
     * 当日请求数
     */
    @TableField
    private Long currentDayRequests;

    /**
     * 当日token数
     */
    @TableField
    private Long currentDayTokens;

    /**
     * 当月费用
     */
    @TableField
    private BigDecimal currentMonthCost;

    /**
     * 最后重置时间
     */
    @TableField
    private LocalDateTime lastResetTime;

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
