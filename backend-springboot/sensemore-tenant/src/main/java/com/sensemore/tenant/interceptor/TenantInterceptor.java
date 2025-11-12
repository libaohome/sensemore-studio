package com.sensemore.tenant.interceptor;

import com.sensemore.tenant.context.TenantContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 多租户拦截器
 * 从请求路径中提取租户ID并设置到上下文
 */
@Slf4j
@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        // 从路径中提取租户ID: /api/tenants/{tenantId}/...
        try {
            String[] parts = requestURI.split("/");
            if (parts.length > 3 && "tenants".equals(parts[2])) {
                String tenantIdStr = parts[3];
                if (tenantIdStr != null && tenantIdStr.matches("\\d+")) {
                    Long tenantId = Long.parseLong(tenantIdStr);
                    TenantContext.setTenantId(tenantId);
                    log.debug("设置租户ID: {}", tenantId);
                }
            }
        } catch (Exception e) {
            log.warn("提取租户ID失败: {}", requestURI, e);
        }

        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable Exception ex) throws Exception {
        // 清理租户ID
        TenantContext.clear();
    }

}
