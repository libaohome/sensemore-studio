package com.sensemore.tenant.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * 数据库初始化配置
 * 按需初始化数据库表和初始数据
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {

    private final DataSource dataSource;

    /**
     * 按需初始化数据库
     */
    @Bean
    public ApplicationRunner databaseInitializer() {
        return args -> {
            try {
                // 检查是否需要初始化
                if (shouldInitialize()) {
                    log.info("数据库需要初始化，开始执行初始化脚本...");
                    initializeDatabase();
                    log.info("数据库初始化完成");
                } else {
                    log.info("数据库已初始化，跳过初始化步骤");
                }
            } catch (Exception e) {
                log.error("数据库初始化失败", e);
            }
        };
    }

    /**
     * 检查是否需要初始化
     */
    private boolean shouldInitialize() {
        try {
            var connection = dataSource.getConnection();
            var metaData = connection.getMetaData();
            var tables = metaData.getTables(null, null, "tenant", null);
            boolean exists = tables.next();
            tables.close();
            connection.close();
            return !exists;
        } catch (Exception e) {
            log.warn("检查表存在性失败，假设需要初始化", e);
            return true;
        }
    }

    /**
     * 执行初始化脚本
     */
    private void initializeDatabase() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db/schema.sql"));
        populator.addScript(new ClassPathResource("db/data.sql"));
        if (dataSource != null) {
            populator.execute(dataSource);
        }
    }

}
