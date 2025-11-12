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
 * 模型配置实体类（租户可配置的参数）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("model_config")
@Schema(name = "ModelConfig", description = "模型配置实体")
public class ModelConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置ID（内部使用，用于索引）
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "配置ID（内部ID，仅用于索引）", example = "1")
    private Long id;

    /**
     * 模型配置业务代码（对外使用）
     */
    @TableField
    @Schema(description = "模型配置业务代码（对外使用）", example = "MODEL1001")
    private String modelCode;

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
     * 配置名称（e.g., 常规回复、创意写作）
     */
    @TableField
    private String configName;

    /**
     * 温度参数（0-2）
     */
    @TableField
    private Double temperature;

    /**
     * Top-P参数（0-1）
     */
    @TableField
    private Double topP;

    /**
     * 最大生成token数
     */
    @TableField
    private Integer maxTokens;

    /**
     * Top-K参数（适用于某些厂商）
     */
    @TableField
    private Integer topK;

    /**
     * 频率惩罚（适用于某些厂商）
     */
    @TableField
    private Double frequencyPenalty;

    /**
     * 存在惩罚（适用于某些厂商）
     */
    @TableField
    private Double presencePenalty;

    /**
     * 停止序列（逗号分隔）
     */
    @TableField
    private String stopSequences;

    /**
     * 系统提示词
     */
    @TableField
    private String systemPrompt;

    /**
     * 状态: 0-禁用, 1-启用
     */
    @TableField
    private Integer status;

    /**
     * 是否为默认配置
     */
    @TableField
    private Integer isDefault;

    /**
     * 描述
     */
    @TableField
    private String description;

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
