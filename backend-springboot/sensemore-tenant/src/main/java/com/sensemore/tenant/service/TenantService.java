package com.sensemore.tenant.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sensemore.tenant.entity.Tenant;
import com.sensemore.tenant.mapper.TenantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 租户管理服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TenantService extends ServiceImpl<TenantMapper, Tenant> {

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
     * 创建租户
     */
    @Transactional
    public void createTenant(Tenant tenant) {
        log.info("创建租户: {}", tenant.getTenantCode());
        tenant.setStatus(1);
        baseMapper.insert(tenant);
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

}
