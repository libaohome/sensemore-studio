package com.sensemore.tenant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 创建AI厂商配置请求 DTO
 */
@Data
@Schema(name = "CreateAiProviderConfigRequest", description = "创建AI厂商配置参数（不包含id、createdAt、updatedAt、status等自动字段）")
public class CreateAiProviderConfigRequest {
    @Schema(description = "配置业务代码", example = "PROVIDER1001")
    private String configCode;

    @Schema(description = "厂商名称", example = "openai")
    private String providerName;

    @Schema(description = "API密钥", example = "sk-xxx")
    private String apiKey;

    @Schema(description = "API密钥（可选）", example = "sk-secret")
    private String apiSecret;

    @Schema(description = "基础URL", example = "https://api.openai.com")
    private String baseUrl;

    @Schema(description = "模型名称", example = "gpt-4")
    private String modelName;

    @Schema(description = "厂商特定配置（JSON）", example = "{\"timeout\":30}")
    private String configJson;

    @Schema(description = "优先级", example = "1")
    private Integer priority;

    @Schema(description = "描述", example = "OpenAI主配置")
    private String description;
}
