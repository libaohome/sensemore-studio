package com.sensemore.tenant.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sensemore.tenant.entity.AiProviderConfig;
import com.sensemore.tenant.mapper.AiProviderConfigMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AI厂商配置服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AiProviderConfigService extends ServiceImpl<AiProviderConfigMapper, AiProviderConfig> {

    /**
     * 获取租户的所有启用的AI厂商配置（使用业务代码）
     */
    public List<AiProviderConfig> getProviderConfigsByTenant(String tenantCode) {
        return baseMapper.selectByTenantCodeAndActive(tenantCode);
    }

    /**
     * 获取租户的特定厂商配置（使用业务代码）
     */
    public AiProviderConfig getProviderConfig(String tenantCode, String providerName) {
        return baseMapper.selectByTenantAndProvider(tenantCode, providerName);
    }

    /**
     * 添加厂商配置（使用业务代码）
     */
    @Transactional
    public void addProviderConfig(AiProviderConfig config) {
        log.info("为租户 {} 添加 {} 厂商配置", config.getTenantCode(), config.getProviderName());
        config.setStatus(1);
        baseMapper.insert(config);
    }

    /**
     * 更新厂商配置（使用业务代码）
     */
    @Transactional
    public void updateProviderConfig(AiProviderConfig config) {
        log.info("更新租户 {} 的配置 Code: {}", config.getTenantCode(), config.getConfigCode());
        baseMapper.updateById(config);
    }

    /**
     * 禁用厂商配置
     */
    @Transactional
    public void disableProviderConfig(Long configId) {
        log.info("禁用配置: {}", configId);
        AiProviderConfig config = new AiProviderConfig();
        config.setId(configId);
        config.setStatus(0);
        baseMapper.updateById(config);
    }

    /**
     * 启用厂商配置
     */
    @Transactional
    public void enableProviderConfig(Long configId) {
        log.info("启用配置: {}", configId);
        AiProviderConfig config = new AiProviderConfig();
        config.setId(configId);
        config.setStatus(1);
        baseMapper.updateById(config);
    }

    /**
     * 删除厂商配置
     */
    @Transactional
    public void deleteProviderConfig(Long configId) {
        log.info("删除配置: {}", configId);
        baseMapper.deleteById(configId);
    }

    /**
     * 获取租户的默认厂商配置
     */
    public AiProviderConfig getDefaultProviderConfig(String tenantCode) {
        List<AiProviderConfig> configs = getProviderConfigsByTenant(tenantCode);
        if (configs.isEmpty()) {
            return null;
        }
        return configs.get(0);
    }

}
