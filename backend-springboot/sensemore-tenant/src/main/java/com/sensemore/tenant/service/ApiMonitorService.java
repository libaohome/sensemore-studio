package com.sensemore.tenant.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sensemore.tenant.entity.ApiCallLog;
import com.sensemore.tenant.entity.ApiUsageStat;
import com.sensemore.tenant.mapper.ApiCallLogMapper;
import com.sensemore.tenant.mapper.ApiUsageStatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * API 调用监控服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiMonitorService {

    private final ApiCallLogMapper apiCallLogMapper;
    private final ApiUsageStatMapper apiUsageStatMapper;

    /**
     * 记录API调用日志
     */
    @Transactional
    public void recordApiCall(ApiCallLog logEntry) {
        log.info("记录API调用: 租户 {} 的厂商配置 {}", logEntry.getTenantId(), logEntry.getProviderConfigId());
        apiCallLogMapper.insert(logEntry);

        // 更新每日统计
        updateDailyStatistic(logEntry);

        // 检查配额
        checkQuotaAndAlert(logEntry.getTenantId(), logEntry.getProviderConfigId(), logEntry.getTotalTokens());
    }

    /**
     * 获取租户的API调用日志
     */
    public List<ApiCallLog> getApiCallLogs(Long tenantId, int pageNum, int pageSize) {
        LambdaQueryWrapper<ApiCallLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApiCallLog::getTenantId, tenantId)
               .orderByDesc(ApiCallLog::getCreatedAt)
               .last("LIMIT " + (pageNum - 1) * pageSize + ", " + pageSize);
        return apiCallLogMapper.selectList(wrapper);
    }

    /**
     * 获取租户的调用统计数据
     */
    public List<ApiUsageStat> getUsageStats(Long tenantId, LocalDate startDate, LocalDate endDate) {
        return apiUsageStatMapper.selectByTenantAndDateRange(tenantId, startDate, endDate);
    }

    /**
     * 获取租户指定日期的统计
     */
    public List<ApiUsageStat> getDailyStats(Long tenantId, LocalDate date) {
        return apiUsageStatMapper.selectByTenantAndDate(tenantId, date);
    }

    /**
     * 更新每日调用统计
     */
    @Transactional
    protected void updateDailyStatistic(ApiCallLog logEntry) {
        LocalDate today = LocalDate.now();

        // 查询是否已有当日记录
        LambdaQueryWrapper<ApiUsageStat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApiUsageStat::getTenantId, logEntry.getTenantId())
               .eq(ApiUsageStat::getProviderConfigId, logEntry.getProviderConfigId())
               .eq(ApiUsageStat::getDateKey, today);

        ApiUsageStat stat = apiUsageStatMapper.selectOne(wrapper);

        if (stat == null) {
            // 创建新记录
            stat = ApiUsageStat.builder()
                    .tenantId(logEntry.getTenantId())
                    .providerConfigId(logEntry.getProviderConfigId())
                    .dateKey(today)
                    .requestCount(1L)
                    .successCount(logEntry.getStatus() == 1 ? 1L : 0L)
                    .failedCount(logEntry.getStatus() == 1 ? 0L : 1L)
                    .totalPromptTokens((long) (logEntry.getPromptTokens() != null ? logEntry.getPromptTokens() : 0))
                    .totalCompletionTokens((long) (logEntry.getCompletionTokens() != null ? logEntry.getCompletionTokens() : 0))
                    .totalTokens((long) (logEntry.getTotalTokens() != null ? logEntry.getTotalTokens() : 0))
                    .totalCost(logEntry.getCost() != null ? logEntry.getCost() : BigDecimal.ZERO)
                    .avgResponseTimeMs(logEntry.getResponseTimeMs() != null ? logEntry.getResponseTimeMs() : 0)
                    .maxResponseTimeMs(logEntry.getResponseTimeMs() != null ? logEntry.getResponseTimeMs() : 0)
                    .minResponseTimeMs(logEntry.getResponseTimeMs() != null ? logEntry.getResponseTimeMs() : Integer.MAX_VALUE)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            apiUsageStatMapper.insert(stat);
        } else {
            // 更新现有记录
            stat.setRequestCount(stat.getRequestCount() + 1);
            stat.setSuccessCount(stat.getSuccessCount() + (logEntry.getStatus() == 1 ? 1 : 0));
            stat.setFailedCount(stat.getFailedCount() + (logEntry.getStatus() == 1 ? 0 : 1));
            stat.setTotalPromptTokens(stat.getTotalPromptTokens() + (logEntry.getPromptTokens() != null ? logEntry.getPromptTokens() : 0));
            stat.setTotalCompletionTokens(stat.getTotalCompletionTokens() + (logEntry.getCompletionTokens() != null ? logEntry.getCompletionTokens() : 0));
            stat.setTotalTokens(stat.getTotalTokens() + (logEntry.getTotalTokens() != null ? logEntry.getTotalTokens() : 0));
            stat.setTotalCost(stat.getTotalCost().add(logEntry.getCost() != null ? logEntry.getCost() : BigDecimal.ZERO));

            // 更新平均/最大/最小响应时间
            if (logEntry.getResponseTimeMs() != null) {
                int avgTime = (int) (stat.getTotalTokens() / stat.getRequestCount());
                stat.setAvgResponseTimeMs(avgTime);
                stat.setMaxResponseTimeMs(Math.max(stat.getMaxResponseTimeMs(), logEntry.getResponseTimeMs()));
                stat.setMinResponseTimeMs(Math.min(stat.getMinResponseTimeMs(), logEntry.getResponseTimeMs()));
            }

            stat.setUpdatedAt(LocalDateTime.now());
            apiUsageStatMapper.updateById(stat);
        }
    }

    /**
     * 检查配额并发送告警
     */
    @Transactional
    protected void checkQuotaAndAlert(Long tenantId, Long providerConfigId, Integer totalTokens) {
        // 配额检查逻辑可扩展实现
        log.debug("检查租户 {} 的配额限制", tenantId);
    }

}
