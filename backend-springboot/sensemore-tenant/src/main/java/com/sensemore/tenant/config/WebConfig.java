package com.sensemore.tenant.config;

import com.sensemore.tenant.interceptor.AuthInterceptor;
import com.sensemore.tenant.interceptor.TenantInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;
    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        if (authInterceptor != null) {
            registry.addInterceptor(authInterceptor)
                    .addPathPatterns("/api/**");
        }
        if (tenantInterceptor != null) {
            registry.addInterceptor(tenantInterceptor)
                    .addPathPatterns("/api/tenants/**")
                    .excludePathPatterns("/api/tenants")  // 排除创建租户的endpoint（无特定租户ID）
            ;
        }
    }

}
