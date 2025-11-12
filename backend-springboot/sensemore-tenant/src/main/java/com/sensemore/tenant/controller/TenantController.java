package com.sensemore.tenant.controller;

import com.sensemore.tenant.dto.ApiResponse;
import com.sensemore.tenant.dto.TenantResponse;
import com.sensemore.tenant.dto.CreateTenantRequest;
import com.sensemore.tenant.dto.CreateTenantResult;
import com.sensemore.tenant.convertor.TenantConvertor;
import com.sensemore.tenant.entity.Tenant;
import com.sensemore.tenant.entity.TenantUser;
import com.sensemore.tenant.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 租户管理 API
 * 
 * 提供租户的创建、查询、启用、禁用、删除等操作
 */
@Slf4j
@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
@Tag(name = "租户管理", description = "租户的创建、查询、启用、禁用、删除操作")
public class TenantController {

    private final TenantService tenantService;
    private final TenantConvertor tenantConvertor;

    /**
     * 创建租户（不允许用户传入id、createdAt、updatedAt、status等字段）
     */
    @PostMapping("/create")
    @Operation(summary = "创建新租户", description = "创建一个新的租户（自动填充id、createdAt、updatedAt、status等字段）")
    public ApiResponse<CreateTenantResult> createTenant(@RequestBody CreateTenantRequest request) {
        try {
            Tenant tenant = new Tenant();
            tenant.setTenantCode(request.getTenantCode());
            tenant.setTenantName(request.getTenantName());
            tenant.setContactPerson(request.getContactPerson());
            tenant.setContactPhone(request.getContactPhone());
            tenant.setContactEmail(request.getContactEmail());
            tenant.setDescription(request.getDescription());
            TenantUser defaultUser = tenantService.createTenant(tenant);

            CreateTenantResult result = new CreateTenantResult();
            result.setTenantCode(tenant.getTenantCode());
            result.setTenantName(tenant.getTenantName());
            result.setDefaultUsername(defaultUser.getUsername());
            result.setDefaultPassword(defaultUser.getPassword());

            return ApiResponse.success(result, "租户创建成功");
        } catch (Exception e) {
            log.error("创建租户失败", e);
            return ApiResponse.fail("租户创建失败: " + e.getMessage());
        }
    }

    /**
     * 获取所有启用的租户
     */
    @GetMapping("/all")
    @Operation(summary = "获取所有启用的租户", description = "返回所有状态为启用的租户列表")
    public ApiResponse<List<TenantResponse>> getActiveTenants() {
        List<Tenant> tenants = tenantService.getActiveTenants();
        List<TenantResponse> tenantResponses = tenantConvertor.toResponseList(tenants);
        return ApiResponse.success(tenantResponses, "获取租户列表成功");
    }

    /**
     * 根据租户代码获取租户
     */
    @GetMapping("/{code}/detail")
    @Operation(summary = "根据租户代码获取租户", description = "根据唯一的租户代码获取租户信息")
    public ApiResponse<TenantResponse> getTenantByCode(
            @Parameter(description = "租户代码", required = true) @PathVariable String code) {
        Tenant tenant = tenantService.getTenantByCode(code);
        if (tenant == null) {
            return ApiResponse.fail("租户不存在");
        }
        TenantResponse tenantResponse = tenantConvertor.toResponse(tenant);
        return ApiResponse.success(tenantResponse, "获取租户成功");
    }
    /**
     * 更新租户信息（通过租户代码）
     */
    @PostMapping("/{code}/update")
    @Operation(summary = "更新租户信息", description = "根据租户代码更新指定租户的信息")
    public ApiResponse<Void> updateTenantByCode(
            @Parameter(description = "租户代码", required = true) @PathVariable String code,
            @RequestBody Tenant tenant) {
        try {
            tenant.setTenantCode(code);
            tenantService.updateByCode(tenant);
            return ApiResponse.success(null, "租户更新成功");
        } catch (Exception e) {
            log.error("更新租户失败", e);
            return ApiResponse.fail("租户更新失败: " + e.getMessage());
        }
    }

    /**
     * 启用租户（通过租户代码）
     */
    @PostMapping("/{code}/enable")
    @Operation(summary = "启用租户", description = "将指定租户的状态更改为启用")
    public ApiResponse<Void> enableTenantByCode(
            @Parameter(description = "租户代码", required = true) @PathVariable String code) {
        try {
            tenantService.enableTenantByCode(code);
            return ApiResponse.success(null, "租户已启用");
        } catch (Exception e) {
            log.error("启用租户失败", e);
            return ApiResponse.fail("启用租户失败: " + e.getMessage());
        }
    }

    /**
     * 禁用租户（通过租户代码）
     */
    @PostMapping("/{code}/disable")
    @Operation(summary = "禁用租户", description = "将指定租户的状态更改为禁用")
    public ApiResponse<Void> disableTenantByCode(
            @Parameter(description = "租户代码", required = true) @PathVariable String code) {
        try {
            tenantService.disableTenantByCode(code);
            return ApiResponse.success(null, "租户已禁用");
        } catch (Exception e) {
            log.error("禁用租户失败", e);
            return ApiResponse.fail("禁用租户失败: " + e.getMessage());
        }
    }

    /**
     * 删除租户（通过租户代码）
     */
    @PostMapping("/{code}/delete")
    @Operation(summary = "删除租户", description = "根据租户代码删除指定的租户及其所有相关数据")
    public ApiResponse<Void> deleteTenantByCode(
            @Parameter(description = "租户代码", required = true) @PathVariable String code) {
        try {
            tenantService.removeByCode(code);
            return ApiResponse.success(null, "租户已删除");
        } catch (Exception e) {
            log.error("删除租户失败", e);
            return ApiResponse.fail("删除租户失败: " + e.getMessage());
        }
    }
}
