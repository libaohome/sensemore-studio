package com.sensemore.tenant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * AI 厂商配置实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("ai_provider_config")
@Schema(name = "AiProviderConfig", description = "AI厂商配置实体")
public class AiProviderConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置ID（内部使用，用于索引）
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "配置ID（内部ID，仅用于索引）", example = "1")
    private Long id;

    /**
     * 配置业务代码（对外使用）
     */
    @TableField
    @Schema(description = "配置业务代码（对外使用）", example = "PROVIDER1001")
    private String configCode;

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
     * 厂商名称: openai, claude, gemini, ollama等
     */
    @TableField
    private String providerName;

    /**
     * API密钥
     */
    @TableField
    private String apiKey;

    /**
     * API密钥（某些厂商需要）
     */
    @TableField
    private String apiSecret;

    /**
     * 基础URL（用于自定义端点或私有部署）
     */
    @TableField
    private String baseUrl;

    /**
     * 使用的模型名称
     */
    @TableField
    private String modelName;

    /**
     * 厂商特定配置（JSON格式）
     */
    @TableField
    private String configJson;

    /**
     * 配置状态: 0-禁用, 1-启用, 2-测试中
     */
    @TableField
    private Integer status;

    /**
     * 优先级（用于多配置时的选择）
     */
    @TableField
    private Integer priority;

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

    /**
     * 获取配置JSON对象
     */
    public JSONObject getConfigJsonObject() {
        return configJson != null ? JSONObject.parseObject(configJson) : new JSONObject();
    }

    /**
     * 设置配置JSON对象
     */
    public void setConfigJsonObject(JSONObject jsonObject) {
        this.configJson = jsonObject != null ? jsonObject.toJSONString() : null;
    }

}
