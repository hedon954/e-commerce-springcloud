package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * <h1>商品微服务启动入口</h1>
 * 启动依赖组件/中间件：Redis、MySQL、Nacos、Kafka、Zipkin
 *
 * swagger2
 * - http://127.0.0.1:8001/ecommerce-goods-service/doc.html
 *
 * @author Hedon Wang
 * @create 2022-06-03 2:23 PM
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }
}
