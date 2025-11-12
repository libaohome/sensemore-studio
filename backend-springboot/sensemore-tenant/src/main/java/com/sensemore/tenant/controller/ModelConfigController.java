package com.sensemore.tenant.controller;

import com.sensemore.tenant.context.TenantContext;
import com.sensemore.tenant.dto.ApiResponse;
import com.sensemore.tenant.entity.AiProviderConfig;
import com.sensemore.tenant.dto.CreateAiProviderConfigRequest;
import com.sensemore.tenant.entity.ModelConfig;
import com.sensemore.tenant.dto.CreateModelConfigRequest;
import com.sensemore.tenant.service.AiProviderConfigService;
import com.sensemore.tenant.service.ModelConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI 模型配置 API
 * 
 * 管理 AI 厂商配置和模型参数配置
 */
@Slf4j
@RestController
@RequestMapping("/tenants/{tenantId}/models")
@RequiredArgsConstructor
@Tag(name = "模型配置管理", description = "AI厂商配置和模型参数配置的管理操作")
public class ModelConfigController {

    private final AiProviderConfigService providerConfigService;
    private final ModelConfigService modelConfigService;

    /**
     * 获取租户的所有 AI 厂商配置
     */
    @GetMapping("/providers")
    @Operation(summary = "获取所有厂商配置", description = "获取当前租户的所有AI厂商配置列表")
    public ApiResponse<List<AiProviderConfig>> getProviderConfigs(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantId) {
        TenantContext.setTenantCode(tenantId);
        try {
            List<AiProviderConfig> configs = providerConfigService.getProviderConfigsByTenant(tenantId);
            return ApiResponse.success(configs, "获取厂商配置列表成功");
        } finally {
            TenantContext.clear();
        }
    }

    /**
     * 获取租户的特定厂商配置
     */
    @GetMapping("/providers/{providerName}")
    @Operation(summary = "获取特定厂商配置", description = "获取指定厂商的配置信息")
    public ApiResponse<AiProviderConfig> getProviderConfig(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantId,
            @Parameter(description = "厂商名称", required = true) @PathVariable String providerName) {
        TenantContext.setTenantCode(tenantId);
        try {
            AiProviderConfig config = providerConfigService.getProviderConfig(tenantId, providerName);
            if (config == null) {
                return ApiResponse.fail("厂商配置不存在");
            }
            return ApiResponse.success(config, "获取厂商配置成功");
        } finally {
            TenantContext.clear();
        }
    }

    /**
     * 添加 AI 厂商配置（不允许用户传入id、createdAt、updatedAt、status等字段）
     */
    @PostMapping("/providers")
    @Operation(summary = "添加厂商配置", description = "为租户添加新的AI厂商配置（自动填充id、createdAt、updatedAt、status等字段）")
    public ApiResponse<Void> addProviderConfig(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantId,
            @RequestBody CreateAiProviderConfigRequest request) {
        TenantContext.setTenantCode(tenantId);
        try {
            AiProviderConfig config = new AiProviderConfig();
            config.setTenantCode(tenantId);
            config.setConfigCode(request.getConfigCode());
            config.setProviderName(request.getProviderName());
            config.setApiKey(request.getApiKey());
            config.setApiSecret(request.getApiSecret());
            config.setBaseUrl(request.getBaseUrl());
            config.setModelName(request.getModelName());
            config.setConfigJson(request.getConfigJson());
            config.setPriority(request.getPriority());
            config.setDescription(request.getDescription());
            config.setStatus(1);
            providerConfigService.save(config);
            return ApiResponse.success(null, "厂商配置添加成功");
        } catch (Exception e) {
            log.error("添加厂商配置失败", e);
            return ApiResponse.fail("添加厂商配置失败: " + e.getMessage());
        } finally {
            TenantContext.clear();
        }
    }

    /**
     * 更新 AI 厂商配置（不允许用户传入id、createdAt、updatedAt、status等字段）
     */
    @PostMapping("/providers/{configId}")
    @Operation(summary = "更新厂商配置", description = "更新指定厂商的配置信息（自动填充id、createdAt、updatedAt、status等字段）")
    public ApiResponse<Void> updateProviderConfig(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantId,
            @Parameter(description = "配置ID", required = true) @PathVariable Long configId,
            @RequestBody CreateAiProviderConfigRequest request) {
        TenantContext.setTenantCode(tenantId);
        try {
            AiProviderConfig config = new AiProviderConfig();
            config.setId(configId);
            config.setTenantCode(tenantId);
            config.setConfigCode(request.getConfigCode());
            config.setProviderName(request.getProviderName());
            config.setApiKey(request.getApiKey());
            config.setApiSecret(request.getApiSecret());
            config.setBaseUrl(request.getBaseUrl());
            config.setModelName(request.getModelName());
            config.setConfigJson(request.getConfigJson());
            config.setPriority(request.getPriority());
            config.setDescription(request.getDescription());
            config.setStatus(1);
            providerConfigService.updateById(config);
            return ApiResponse.success(null, "厂商配置更新成功");
        } catch (Exception e) {
            log.error("更新厂商配置失败", e);
            return ApiResponse.fail("更新厂商配置失败: " + e.getMessage());
        } finally {
            TenantContext.clear();
        }
    }

    /**
     * 删除 AI 厂商配置
     */
    @PostMapping("/providers/{configId}/delete")
    @Operation(summary = "删除厂商配置", description = "删除指定的厂商配置")
    public ApiResponse<Void> deleteProviderConfig(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantId,
            @Parameter(description = "配置ID", required = true) @PathVariable Long configId) {
        TenantContext.setTenantCode(tenantId);
        try {
            providerConfigService.removeById(configId);
            return ApiResponse.success(null, "厂商配置删除成功");
        } catch (Exception e) {
            log.error("删除厂商配置失败", e);
            return ApiResponse.fail("删除厂商配置失败: " + e.getMessage());
        } finally {
            TenantContext.clear();
        }
    }

    /**
     * 获取租户的所有模型配置
     */
    @GetMapping
    @Operation(summary = "获取所有模型配置", description = "获取当前租户的所有模型配置")
    public ApiResponse<List<ModelConfig>> getModelConfigs(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantId) {
        TenantContext.setTenantCode(tenantId);
        try {
            List<ModelConfig> configs = modelConfigService.getModelConfigsByTenant(tenantId);
            return ApiResponse.success(configs, "获取模型配置列表成功");
        } finally {
            TenantContext.clear();
        }
    }

    /**
     * 获取默认模型配置
     */
    @GetMapping("/default")
    @Operation(summary = "获取默认模型配置", description = "获取当前租户的默认模型配置")
    public ApiResponse<ModelConfig> getDefaultModelConfig(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantId) {
        TenantContext.setTenantCode(tenantId);
        try {
            ModelConfig config = modelConfigService.getDefaultModelConfig(tenantId);
            if (config == null) {
                return ApiResponse.fail("默认配置不存在");
            }
            return ApiResponse.success(config, "获取默认配置成功");
        } finally {
            TenantContext.clear();
        }
    }

    /**
     * 创建模型配置（不允许用户传入id、createdAt、updatedAt、status等字段）
     */
    @PostMapping
    @Operation(summary = "创建模型配置", description = "创建新的模型配置（自动填充id、createdAt、updatedAt、status等字段）")
    public ApiResponse<Void> createModelConfig(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantId,
            @RequestBody CreateModelConfigRequest request) {
        TenantContext.setTenantCode(tenantId);
        try {
            ModelConfig config = new ModelConfig();
            config.setTenantCode(tenantId);
            config.setModelCode(request.getModelCode());
            config.setConfigCode(request.getConfigCode());
            config.setConfigName(request.getConfigName());
            config.setTemperature(request.getTemperature());
            config.setTopP(request.getTopP());
            config.setMaxTokens(request.getMaxTokens());
            config.setTopK(request.getTopK());
            config.setFrequencyPenalty(request.getFrequencyPenalty());
            config.setPresencePenalty(request.getPresencePenalty());
            config.setStopSequences(request.getStopSequences());
            config.setSystemPrompt(request.getSystemPrompt());
            config.setDescription(request.getDescription());
            config.setStatus(1);
            modelConfigService.save(config);
            return ApiResponse.success(null, "模型配置创建成功");
        } catch (Exception e) {
            log.error("创建模型配置失败", e);
            return ApiResponse.fail("创建模型配置失败: " + e.getMessage());
        } finally {
            TenantContext.clear();
        }
    }

    /**
     * 更新模型配置（不允许用户传入id、createdAt、updatedAt、status等字段）
     */
    @PostMapping("/{configId}")
    @Operation(summary = "更新模型配置", description = "更新指定模型的配置信息（自动填充id、createdAt、updatedAt、status等字段）")
    public ApiResponse<Void> updateModelConfig(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantId,
            @Parameter(description = "配置ID", required = true) @PathVariable Long configId,
            @RequestBody CreateModelConfigRequest request) {
        TenantContext.setTenantCode(tenantId);
        try {
            ModelConfig config = new ModelConfig();
            config.setId(configId);
            config.setTenantCode(tenantId);
            config.setModelCode(request.getModelCode());
            config.setConfigCode(request.getConfigCode());
            config.setConfigName(request.getConfigName());
            config.setTemperature(request.getTemperature());
            config.setTopP(request.getTopP());
            config.setMaxTokens(request.getMaxTokens());
            config.setTopK(request.getTopK());
            config.setFrequencyPenalty(request.getFrequencyPenalty());
            config.setPresencePenalty(request.getPresencePenalty());
            config.setStopSequences(request.getStopSequences());
            config.setSystemPrompt(request.getSystemPrompt());
            config.setDescription(request.getDescription());
            config.setStatus(1);
            modelConfigService.updateById(config);
            return ApiResponse.success(null, "模型配置更新成功");
        } catch (Exception e) {
            log.error("更新模型配置失败", e);
            return ApiResponse.fail("更新模型配置失败: " + e.getMessage());
        } finally {
            TenantContext.clear();
        }
    }

    /**
     * 设置默认模型配置
     */
    @PostMapping("/{configId}/set-default")
    @Operation(summary = "设置默认模型配置", description = "将指定模型配置设为默认")
    public ApiResponse<Void> setDefaultModelConfig(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantId,
            @Parameter(description = "配置ID", required = true) @PathVariable Long configId) {
        TenantContext.setTenantCode(tenantId);
        try {
            modelConfigService.setAsDefault(tenantId, configId);
            return ApiResponse.success(null, "默认配置设置成功");
        } catch (Exception e) {
            log.error("设置默认配置失败", e);
            return ApiResponse.fail("设置默认配置失败: " + e.getMessage());
        } finally {
            TenantContext.clear();
        }
    }

    /**
     * 删除模型配置
     */
    @PostMapping("/{configId}/delete")
    @Operation(summary = "删除模型配置", description = "删除指定的模型配置")
    public ApiResponse<Void> deleteModelConfig(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantId,
            @Parameter(description = "配置ID", required = true) @PathVariable Long configId) {
        TenantContext.setTenantCode(tenantId);
        try {
            modelConfigService.removeById(configId);
            return ApiResponse.success(null, "模型配置删除成功");
        } catch (Exception e) {
            log.error("删除模型配置失败", e);
            return ApiResponse.fail("删除模型配置失败: " + e.getMessage());
        } finally {
            TenantContext.clear();
        }
    }
}
