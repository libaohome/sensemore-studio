package com.sensemore.tenant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**") // 匹配所有路径
                // .allowedOrigins("http://localhost:5173", "http://127.0.0.1", "http://127.0.0.1:8082") // 多域名用逗号分隔
                .allowedOriginPatterns("http://localhost:*") // 支持通配符
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法
                .allowedHeaders("*") // 允许所有请求头
                .allowCredentials(true) // 允许携带凭证
                .maxAge(3600); // 预检请求缓存时间（秒）
    }
}
