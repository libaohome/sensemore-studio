package com.sensemore.tenant.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sensemore.tenant.entity.Tenant;
import com.sensemore.tenant.entity.TenantUser;
import com.sensemore.tenant.mapper.TenantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 租户管理服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TenantService extends ServiceImpl<TenantMapper, Tenant> {

    private final TenantUserService tenantUserService;

    /**
     * 根据租户代码获取租户
     */
    public Tenant getTenantByCode(String tenantCode) {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getTenantCode, tenantCode)
               .eq(Tenant::getStatus, 1);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 获取所有启用的租户
     */
    public List<Tenant> getActiveTenants() {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getStatus, 1);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 创建租户并生成默认管理员用户
     */
    @Transactional
    public TenantUser createTenant(Tenant tenant) {
        log.info("创建租户: {}", tenant.getTenantCode());
        LocalDateTime now = LocalDateTime.now();
        tenant.setStatus(1);
        if (tenant.getCreatedAt() == null) {
            tenant.setCreatedAt(now);
        }
        tenant.setUpdatedAt(now);
        baseMapper.insert(tenant);

        String contactEmail = normalizeText(tenant.getContactEmail());
        String contactPhone = normalizeText(tenant.getContactPhone());
        String defaultUsername = deriveDefaultUsername(tenant.getTenantCode(), contactEmail);
        String defaultPassword = buildDefaultPassword(tenant.getTenantName(), tenant.getTenantCode(), contactEmail);

        TenantUser defaultUser = new TenantUser();
        defaultUser.setTenantCode(tenant.getTenantCode());
        defaultUser.setUsername(defaultUsername);
        defaultUser.setPassword(defaultPassword);
        defaultUser.setEmail(StringUtils.hasText(contactEmail) ? contactEmail : null);
        defaultUser.setPhone(StringUtils.hasText(contactPhone) ? contactPhone : null);
        defaultUser.setCreatedAt(now);
        defaultUser.setUpdatedAt(now);

        tenantUserService.createUser(defaultUser);
        return defaultUser;
    }

    /**
     * 禁用租户（通过租户代码）
     */
    @Transactional
    public void disableTenantByCode(String tenantCode) {
        log.info("禁用租户: {}", tenantCode);
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getTenantCode, tenantCode);
        Tenant tenant = baseMapper.selectOne(wrapper);
        if (tenant != null) {
            tenant.setStatus(0);
            baseMapper.updateById(tenant);
        }
    }

    /**
     * 启用租户（通过租户代码）
     */
    @Transactional
    public void enableTenantByCode(String tenantCode) {
        log.info("启用租户: {}", tenantCode);
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getTenantCode, tenantCode);
        Tenant tenant = baseMapper.selectOne(wrapper);
        if (tenant != null) {
            tenant.setStatus(1);
            baseMapper.updateById(tenant);
        }
    }

    /**
     * 更新租户信息（通过租户代码）
     */
    @Transactional
    public void updateByCode(Tenant tenant) {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getTenantCode, tenant.getTenantCode());
        Tenant dbTenant = baseMapper.selectOne(wrapper);
        if (dbTenant != null) {
            tenant.setId(dbTenant.getId());
            tenant.setUpdatedAt(LocalDateTime.now());
            baseMapper.updateById(tenant);
        }
    }

    /**
     * 删除租户（通过租户代码）
     */
    @Transactional
    public void removeByCode(String tenantCode) {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getTenantCode, tenantCode);
        baseMapper.delete(wrapper);
    }

    private String deriveDefaultUsername(String tenantCode, String contactEmail) {
        if (StringUtils.hasText(contactEmail) && contactEmail.contains("@")) {
            return contactEmail.substring(0, contactEmail.indexOf('@'));
        }
        return tenantCode + "_admin";
    }

    private String buildDefaultPassword(String tenantName, String tenantCode, String contactEmail) {
        String prefix = StringUtils.hasText(tenantName) ? tenantName : tenantCode;
        String suffix = StringUtils.hasText(contactEmail) ? contactEmail : "";
        return prefix + suffix;
    }

    private String normalizeText(String source) {
        if (source == null) {
            return null;
        }
        String trimmed = source.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

}
