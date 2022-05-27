package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Hedon Wang
 * @create 2022-05-24 3:27 PM
 */
@EnableJpaAuditing   // 允许 JPA 自动审计（会自动更新 create_time 和 update_time
@SpringBootApplication
@EnableDiscoveryClient
public class AuthorityCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityCenterApplication.class, args);
    }

}
