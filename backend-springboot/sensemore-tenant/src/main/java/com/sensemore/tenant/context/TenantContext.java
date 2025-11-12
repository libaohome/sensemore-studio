package com.sensemore.tenant.context;

/**
 * 多租户上下文管理
 * 用于在线程中存储当前租户ID和租户代码
 */
public class TenantContext {

    private static final ThreadLocal<Long> TENANT_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> TENANT_CODE = new ThreadLocal<>();

    /**
     * 设置当前租户ID
     */
    public static void setTenantId(Long tenantId) {
        TENANT_ID.set(tenantId);
    }

    /**
     * 获取当前租户ID
     */
    public static Long getTenantId() {
        return TENANT_ID.get();
    }

    /**
     * 设置当前租户代码
     */
    public static void setTenantCode(String tenantCode) {
        TENANT_CODE.set(tenantCode);
    }

    /**
     * 获取当前租户代码
     */
    public static String getTenantCode() {
        return TENANT_CODE.get();
    }

    /**
     * 清除租户信息
     */
    public static void clear() {
        TENANT_ID.remove();
        TENANT_CODE.remove();
    }

    /**
     * 检查是否已设置租户ID
     */
    public static boolean hasTenantId() {
        return TENANT_ID.get() != null;
    }

    /**
     * 检查是否已设置租户代码
     */
    public static boolean hasTenantCode() {
        return TENANT_CODE.get() != null;
    }

}
