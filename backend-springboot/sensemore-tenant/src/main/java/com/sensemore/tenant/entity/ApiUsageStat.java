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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * API 调用量统计实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("api_usage_stat")
@Schema(name = "ApiUsageStat", description = "API调用量统计实体")
public class ApiUsageStat implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 统计ID（内部使用，用于索引）
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "统计ID（内部ID，仅用于索引）", example = "1")
    private Long id;

    /**
     * 统计业务代码（对外使用）
     */
    @TableField
    @Schema(description = "统计业务代码（对外使用）", example = "STAT1001")
    private String statCode;

    /**
     * 租户ID（内部使用，保留用于数据库查询）
     */
    @TableField
    @Schema(hidden = true)
    private Long tenantId;

    /**
     * 租户业务代码（对外使用）
     */
    @TableField
    @Schema(description = "租户业务代码", example = "TENANT1001")
    private String tenantCode;

    /**
     * AI厂商配置ID（内部使用，保留用于数据库查询）
     */
    @TableField
    @Schema(hidden = true)
    private Long providerConfigId;

    /**
     * 厂商配置业务代码（对外使用）
     */
    @TableField
    @Schema(description = "厂商配置业务代码", example = "PROVIDER1001")
    private String configCode;

    /**
     * 统计日期
     */
    @TableField
    private LocalDate dateKey;

    /**
     * 请求数量
     */
    @TableField
    private Long requestCount;

    /**
     * 成功请求数
     */
    @TableField
    private Long successCount;

    /**
     * 失败请求数
     */
    @TableField
    private Long failedCount;

    /**
     * 总输入token数
     */
    @TableField
    private Long totalPromptTokens;

    /**
     * 总输出token数
     */
    @TableField
    private Long totalCompletionTokens;

    /**
     * 总token数
     */
    @TableField
    private Long totalTokens;

    /**
     * 总费用
     */
    @TableField
    private BigDecimal totalCost;

    /**
     * 平均响应时间(ms)
     */
    @TableField
    private Integer avgResponseTimeMs;

    /**
     * 最大响应时间(ms)
     */
    @TableField
    private Integer maxResponseTimeMs;

    /**
     * 最小响应时间(ms)
     */
    @TableField
    private Integer minResponseTimeMs;

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
