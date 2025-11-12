package com.sensemore.tenant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Swagger/OpenAPI 配置类
 * 
 * 提供 API 文档的元数据和配置
 * 访问地址: http://localhost:8081/swagger-ui.html
 * JSON 地址: http://localhost:8081/v3/api-docs
 */
@Configuration
public class OpenApiConfig {

    /**
     * 配置 OpenAPI 文档信息
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sensemore Tenant AI 管理系统 API")
                        .description("多租户 AI 模型管理系统，支持租户隔离、模型配置、调用监控、配额管理和告警规则")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sensemore Team")
                                .email("support@sensemore.com")
                                .url("https://www.sensemore.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(Arrays.asList(
                        new Server()
                                .url("http://localhost:8081/api")
                                .description("本地开发环境"),
                        new Server()
                                .url("http://localhost:8081/api")
                                .description("生产环境（需配置）")
                ));
    }
}
