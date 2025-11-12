package com.sensemore.tenant.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sensemore.tenant.entity.AlertRule;
import com.sensemore.tenant.mapper.AlertRuleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 告警规则服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlertRuleService extends ServiceImpl<AlertRuleMapper, AlertRule> {

    private final QuotaLimitService quotaLimitService;

    /**
     * 获取租户的所有启用告警规则
     */
    public List<AlertRule> getActiveAlertRules(String tenantCode) {
        return baseMapper.selectByTenantCodeAndActive(tenantCode);
    }

    /**
     * 获取特定类型的告警规则
     */
    public List<AlertRule> getAlertRulesByType(String tenantCode, String ruleType) {
        return baseMapper.selectByTenantAndRuleType(tenantCode, ruleType);
    }

    /**
     * 创建告警规则
     */
    @Transactional
    public void createAlertRule(AlertRule alertRule) {
        log.info("为租户 {} 创建告警规则: {}", alertRule.getTenantCode(), alertRule.getRuleName());
        alertRule.setStatus(1);
        baseMapper.insert(alertRule);
    }

    /**
     * 更新告警规则
     */
    @Transactional
    public void updateAlertRule(AlertRule alertRule) {
        log.info("更新告警规则: {} - {}", alertRule.getId(), alertRule.getRuleName());
        baseMapper.updateById(alertRule);
    }

    /**
     * 禁用告警规则
     */
    @Transactional
    public void disableAlertRule(Long ruleId) {
        log.info("禁用告警规则: {}", ruleId);
        AlertRule alertRule = new AlertRule();
        alertRule.setId(ruleId);
        alertRule.setStatus(0);
        baseMapper.updateById(alertRule);
    }

    /**
     * 删除告警规则
     */
    @Transactional
    public void deleteAlertRule(Long ruleId) {
        log.info("删除告警规则: {}", ruleId);
        baseMapper.deleteById(ruleId);
    }

    /**
     * 检查并触发告警
     */
    public void checkAndTriggerAlerts(String tenantCode, Long providerConfigId) {
        log.debug("检查租户 {} 的告警规则", tenantCode);

        List<AlertRule> rules = getActiveAlertRules(tenantCode);
        for (AlertRule rule : rules) {
            if (shouldTriggerAlert(tenantCode, rule)) {
                triggerAlert(rule);
            }
        }
    }

    /**
     * 判断是否应该触发告警
     */
    private boolean shouldTriggerAlert(String tenantCode, AlertRule rule) {
        switch (rule.getRuleType()) {
            case "quota_exceeded":
                return quotaLimitService.isExceedDailyRequestLimit(tenantCode);
            case "cost_exceeded":
                return quotaLimitService.isExceedMonthlyCostLimit(tenantCode);
            case "error_rate_high":
                // 可根据需要扩展
                return false;
            default:
                return false;
        }
    }

    /**
     * 触发告警（发送通知）
     */
    private void triggerAlert(AlertRule rule) {
        log.warn("触发告警规则: {} - {}", rule.getId(), rule.getRuleName());

        if (rule.getAlertEmail() != null && !rule.getAlertEmail().isEmpty()) {
            sendEmailAlert(rule);
        }

        if (rule.getAlertPhone() != null && !rule.getAlertPhone().isEmpty()) {
            sendPhoneAlert(rule);
        }
    }

    /**
     * 发送邮件告警
     */
    private void sendEmailAlert(AlertRule rule) {
        String[] emails = rule.getAlertEmail().split(",");
        for (String email : emails) {
            log.info("发送邮件告警至: {}", email);
            // 实际实现可通过邮件服务发送
        }
    }

    /**
     * 发送短信告警
     */
    private void sendPhoneAlert(AlertRule rule) {
        String[] phones = rule.getAlertPhone().split(",");
        for (String phone : phones) {
            log.info("发送短信告警至: {}", phone);
            // 实际实现可通过短信服务发送
        }
    }

}
