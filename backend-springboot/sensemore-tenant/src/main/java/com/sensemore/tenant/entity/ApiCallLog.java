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
 * API 调用日志实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("api_call_log")
@Schema(name = "ApiCallLog", description = "API调用日志实体")
public class ApiCallLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID（内部使用，用于索引）
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "日志ID（内部ID，仅用于索引）", example = "1")
    private Long id;

    /**
     * 日志业务代码（对外使用）
     */
    @TableField
    @Schema(description = "日志业务代码（对外使用）", example = "LOG1001")
    private String logCode;

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
     * 模型配置ID（内部使用，保留用于数据库查询）
     */
    @TableField
    @Schema(hidden = true)
    private Long modelConfigId;

    /**
     * 模型配置业务代码（对外使用）
     */
    @TableField
    @Schema(description = "模型配置业务代码", example = "MODEL1001")
    private String modelCode;

    /**
     * 用户ID
     */
    @TableField
    private String userId;

    /**
     * 请求内容
     */
    @TableField
    private String requestContent;

    /**
     * 响应内容
     */
    @TableField
    private String responseContent;

    /**
     * 输入token数
     */
    @TableField
    private Integer promptTokens;

    /**
     * 输出token数
     */
    @TableField
    private Integer completionTokens;

    /**
     * 总token数
     */
    @TableField
    private Integer totalTokens;

    /**
     * 本次调用费用
     */
    @TableField
    private BigDecimal cost;

    /**
     * 响应时间(ms)
     */
    @TableField
    private Integer responseTimeMs;

    /**
     * 状态: 0-失败, 1-成功
     */
    @TableField
    private Integer status;

    /**
     * 错误信息
     */
    @TableField
    private String errorMessage;

    /**
     * 创建时间
     */
    @TableField
    private LocalDateTime createdAt;

}
