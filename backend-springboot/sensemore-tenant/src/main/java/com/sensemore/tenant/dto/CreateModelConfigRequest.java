package com.sensemore.tenant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 创建模型配置请求 DTO
 */
@Data
@Schema(name = "CreateModelConfigRequest", description = "创建模型配置参数（不包含id、createdAt、updatedAt、status等自动字段）")
public class CreateModelConfigRequest {
    @Schema(description = "模型配置业务代码", example = "MODEL1001")
    private String modelCode;

    @Schema(description = "厂商配置业务代码", example = "PROVIDER1001")
    private String configCode;

    @Schema(description = "配置名称", example = "常规回复")
    private String configName;

    @Schema(description = "温度参数", example = "0.7")
    private Double temperature;

    @Schema(description = "Top-P参数", example = "0.9")
    private Double topP;

    @Schema(description = "最大生成token数", example = "2000")
    private Integer maxTokens;

    @Schema(description = "Top-K参数", example = "5")
    private Integer topK;

    @Schema(description = "频率惩罚", example = "0.1")
    private Double frequencyPenalty;

    @Schema(description = "存在惩罚", example = "0.1")
    private Double presencePenalty;

    @Schema(description = "停止序列", example = "END")
    private String stopSequences;

    @Schema(description = "系统提示词", example = "你是一个AI助手")
    private String systemPrompt;

    @Schema(description = "描述", example = "常规模型配置")
    private String description;
}
