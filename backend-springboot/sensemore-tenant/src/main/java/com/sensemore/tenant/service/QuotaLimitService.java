package com.sensemore.tenant.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sensemore.tenant.entity.QuotaLimit;
import com.sensemore.tenant.mapper.QuotaLimitMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 配额管理服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QuotaLimitService extends ServiceImpl<QuotaLimitMapper, QuotaLimit> {

    /**
     * 获取租户配额
     */
    public QuotaLimit getTenantQuota(String tenantCode) {
        return baseMapper.selectByTenantCode(tenantCode);
    }

    /**
     * 创建配额限制
     */
    @Transactional
    public void createQuotaLimit(QuotaLimit quotaLimit) {
        log.info("为租户 {} 创建配额限制", quotaLimit.getTenantCode());
        quotaLimit.setStatus(1);
        quotaLimit.setLastResetTime(LocalDateTime.now());
        baseMapper.insert(quotaLimit);
    }

    /**
     * 更新配额使用统计
     */
    @Transactional
    public void updateQuotaUsage(String tenantCode, Integer tokens) {
        QuotaLimit quota = getTenantQuota(tenantCode);
        if (quota == null || quota.getStatus() == 0) {
            return;
        }

        // 增加当日使用量
        quota.setCurrentDayRequests(quota.getCurrentDayRequests() != null ? quota.getCurrentDayRequests() + 1 : 1);
        quota.setCurrentDayTokens(quota.getCurrentDayTokens() != null ? quota.getCurrentDayTokens() + tokens : tokens);

        baseMapper.updateById(quota);
    }

    /**
     * 检查是否超过日请求限制
     */
    public boolean isExceedDailyRequestLimit(String tenantCode) {
        QuotaLimit quota = getTenantQuota(tenantCode);
        if (quota == null || quota.getDailyRequestLimit() < 0) {
            return false;
        }
        return quota.getCurrentDayRequests() != null && quota.getCurrentDayRequests() >= quota.getDailyRequestLimit();
    }

    /**
     * 检查是否超过日token限制
     */
    public boolean isExceedDailyTokenLimit(String tenantCode) {
        QuotaLimit quota = getTenantQuota(tenantCode);
        if (quota == null || quota.getDailyTokenLimit() < 0) {
            return false;
        }
        return quota.getCurrentDayTokens() != null && quota.getCurrentDayTokens() >= quota.getDailyTokenLimit();
    }

    /**
     * 检查是否超过月费用限制
     */
    public boolean isExceedMonthlyCostLimit(String tenantCode) {
        QuotaLimit quota = getTenantQuota(tenantCode);
        if (quota == null || quota.getMonthlyCostLimit().compareTo(BigDecimal.valueOf(-1)) < 0) {
            return false;
        }
        return quota.getCurrentMonthCost() != null && quota.getCurrentMonthCost().compareTo(quota.getMonthlyCostLimit()) >= 0;
    }

    /**
     * 重置日配额
     */
    @Transactional
    public void resetDailyQuota(String tenantCode) {
        log.info("重置租户 {} 的日配额", tenantCode);
        QuotaLimit quota = new QuotaLimit();
        quota.setTenantCode(tenantCode);
        quota.setCurrentDayRequests(0L);
        quota.setCurrentDayTokens(0L);
        quota.setLastResetTime(LocalDateTime.now());

        LambdaQueryWrapper<QuotaLimit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(QuotaLimit::getTenantCode, tenantCode);
        baseMapper.update(quota, wrapper);
    }

    /**
     * 禁用配额限制
     */
    @Transactional
    public void disableQuotaLimit(String tenantCode) {
        log.info("禁用租户 {} 的配额限制", tenantCode);
        QuotaLimit quota = new QuotaLimit();
        quota.setTenantCode(tenantCode);
        quota.setStatus(0);

        LambdaQueryWrapper<QuotaLimit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(QuotaLimit::getTenantCode, tenantCode);
        baseMapper.update(quota, wrapper);
    }

}
