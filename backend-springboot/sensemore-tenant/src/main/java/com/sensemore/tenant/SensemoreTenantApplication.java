package com.sensemore.tenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 多租户 AI 管理系统主程序
 */
@SpringBootApplication
@EnableTransactionManagement
public class SensemoreTenantApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensemoreTenantApplication.class, args);
	}

}
