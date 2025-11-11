package com.sensemore.aistudio.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import java.util.Objects;

/**
 * On application start, check whether the required tables exist. If not,
 * execute the schema and data SQL scripts from classpath.
 *
 * This allows keeping `spring.sql.init.mode: never` in application.yml while
 * still initializing an empty database on first run.
 */
@Component
@Order(0)
public class DatabaseInitializer implements ApplicationRunner {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

    public DatabaseInitializer(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            // quick existence check for ai_config table
            jdbcTemplate.queryForObject("SELECT 1 FROM ai_config LIMIT 1", Integer.class);
            log.info("ai_config table exists — skipping automatic DB initialization");
        } catch (Exception ex) {
            log.info("ai_config table not found — executing schema and data SQL scripts");
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new ClassPathResource("db/schema-h2.sql"));
            populator.addScript(new ClassPathResource("db/data-h2.sql"));
            populator.execute(Objects.requireNonNull(dataSource, "DataSource must not be null"));
            log.info("Database initialization finished");
        }
    }
}
