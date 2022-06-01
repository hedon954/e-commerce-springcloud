package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * <h1>用户账户微服务入口</h1>
 *
 * swagger 地址
 * - 127.0.0.1:8003/ecommerce-account-service/doc.html
 * - 127.0.0.1:8003/ecommerce-account-service/swagger-ui.html
 *
 * @author Hedon Wang
 * @create 2022-05-31 4:07 PM
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaAuditing
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class);
    }
}
