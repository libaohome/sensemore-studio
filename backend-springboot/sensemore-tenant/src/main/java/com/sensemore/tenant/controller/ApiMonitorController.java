package com.sensemore.tenant.controller;

import com.sensemore.tenant.context.TenantContext;
import com.sensemore.tenant.dto.ApiResponse;
import com.sensemore.tenant.entity.ApiCallLog;
import com.sensemore.tenant.entity.ApiUsageStat;
import com.sensemore.tenant.service.ApiMonitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * API 监控和统计 API
 * 
 * 提供 API 调用日志查询和统计数据
 */
@Slf4j
@RestController
@RequestMapping("/tenants/{tenantId}/monitor")
@RequiredArgsConstructor
@Tag(name = "API监控统计", description = "API调用日志查询、每日统计、日期范围统计")
public class ApiMonitorController {

    private final ApiMonitorService apiMonitorService;

    /**
     * 获取 API 调用日志（分页）
     */
    @GetMapping("/logs")
    @Operation(summary = "获取API调用日志", description = "获取租户的API调用详细日志，支持分页查询")
    public ApiResponse<List<ApiCallLog>> getApiCallLogs(
            @Parameter(description = "租户ID", required = true) @PathVariable Long tenantId,
            @Parameter(description = "页码", required = false) @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页数量", required = false) @RequestParam(defaultValue = "10") Integer pageSize) {
        TenantContext.setTenantId(tenantId);
        try {
            List<ApiCallLog> logs = apiMonitorService.getApiCallLogs(tenantId, pageNum, pageSize);
            return ApiResponse.success(logs, "获取API调用日志成功");
        } finally {
            TenantContext.clear();
        }
    }

    /**
     * 获取每日统计数据
     */
    @GetMapping("/stats/daily")
    @Operation(summary = "获取每日统计数据", description = "获取租户的每日API调用统计数据")
    public ApiResponse<List<ApiUsageStat>> getDailyStats(
            @Parameter(description = "租户ID", required = true) @PathVariable Long tenantId,
            @Parameter(description = "统计日期(yyyy-MM-dd)", required = true) @RequestParam String date) {
        TenantContext.setTenantId(tenantId);
        try {
            LocalDate statsDate = LocalDate.parse(date);
            List<ApiUsageStat> stats = apiMonitorService.getDailyStats(tenantId, statsDate);
            return ApiResponse.success(stats, "获取每日统计成功");
        } catch (Exception e) {
            log.error("获取每日统计失败", e);
            return ApiResponse.fail("获取每日统计失败: " + e.getMessage());
        } finally {
            TenantContext.clear();
        }
    }

    /**
     * 获取日期范围内的统计数据
     */
    @GetMapping("/stats/range")
    @Operation(summary = "获取日期范围统计", description = "获取指定日期范围内的API调用统计数据")
    public ApiResponse<List<ApiUsageStat>> getStatsInDateRange(
            @Parameter(description = "租户ID", required = true) @PathVariable Long tenantId,
            @Parameter(description = "开始日期(yyyy-MM-dd)", required = true) @RequestParam String startDate,
            @Parameter(description = "结束日期(yyyy-MM-dd)", required = true) @RequestParam String endDate) {
        TenantContext.setTenantId(tenantId);
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            List<ApiUsageStat> stats = apiMonitorService.getUsageStats(tenantId, start, end);
            return ApiResponse.success(stats, "获取日期范围统计成功");
        } catch (Exception e) {
            log.error("获取日期范围统计失败", e);
            return ApiResponse.fail("获取日期范围统计失败: " + e.getMessage());
        } finally {
            TenantContext.clear();
        }
    }
}
