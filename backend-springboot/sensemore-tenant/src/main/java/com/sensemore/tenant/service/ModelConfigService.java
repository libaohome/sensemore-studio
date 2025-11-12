package com.sensemore.tenant.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sensemore.tenant.entity.ModelConfig;
import com.sensemore.tenant.mapper.ModelConfigMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 模型配置服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ModelConfigService extends ServiceImpl<ModelConfigMapper, ModelConfig> {

    /**
     * 获取租户的所有启用模型配置
     */
    public List<ModelConfig> getModelConfigsByTenant(String tenantCode) {
        return baseMapper.selectByTenantCodeAndActive(tenantCode);
    }

    /**
     * 获取租户的默认模型配置
     */
    public ModelConfig getDefaultModelConfig(String tenantCode) {
        return baseMapper.selectDefaultByTenant(tenantCode);
    }

    /**
     * 获取AI厂商配置下的所有模型配置
     */
    public List<ModelConfig> getModelConfigsByProviderConfig(Long providerConfigId) {
        return baseMapper.selectByProviderConfigId(providerConfigId);
    }

    /**
     * 创建模型配置
     */
    @Transactional
    public void createModelConfig(ModelConfig config) {
        log.info("为租户 {} 创建模型配置: {}", config.getTenantCode(), config.getConfigName());
        config.setStatus(1);
        baseMapper.insert(config);
    }

    /**
     * 更新模型配置
     */
    @Transactional
    public void updateModelConfig(ModelConfig config) {
        log.info("更新模型配置 ID: {}", config.getId());
        baseMapper.updateById(config);
    }

    /**
     * 设置为默认配置
     */
    @Transactional
    public void setAsDefault(String tenantCode, Long configId) {
        log.info("设置租户 {} 的配置 {} 为默认", tenantCode, configId);

        // 清除其他默认配置
        List<ModelConfig> configs = getModelConfigsByTenant(tenantCode);
        for (ModelConfig config : configs) {
            if (config.getIsDefault() != null && config.getIsDefault() == 1) {
                config.setIsDefault(0);
                baseMapper.updateById(config);
            }
        }

        // 设置新的默认配置
        ModelConfig config = new ModelConfig();
        config.setId(configId);
        config.setIsDefault(1);
        baseMapper.updateById(config);
    }

    /**
     * 删除模型配置
     */
    @Transactional
    public void deleteModelConfig(Long configId) {
        log.info("删除模型配置: {}", configId);
        baseMapper.deleteById(configId);
    }

}
