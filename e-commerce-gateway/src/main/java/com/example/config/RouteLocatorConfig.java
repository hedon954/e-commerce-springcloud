package com.example.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>配置登录请求转发规则</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-27 2:06 PM
 */
@Configuration
public class RouteLocatorConfig {

    /**
     * <h2>使用代码去定义路由规则，在网关层面拦截下登录和注册接口</h2>
     */
    @Bean
    public RouteLocator loginRouteLocator(RouteLocatorBuilder builder){

        // 手动定义 Gateway 路由规则需要指定：id、path、uri
        return builder.routes()
                .route(
                        "e_commerce_authority",
                        r -> r.path("/imooc/e-commerce/login", "/imooc/e-commerce/register")
                                .uri("http://localhost:9001/")
                ).build();
    }

}
