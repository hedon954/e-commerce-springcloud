package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>配置类，用于读取 Nacos 配置中心</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-25 2:15 PM
 */
@Configuration
public class GatewayConfig {

    /** 读取配置的超时时间 */
    public static final long DEFAULT_TIMEOUT_MS = 30000;

    /** Nacos 服务器地址 */
    public static String NACOS_SERVER_ADDR;

    /** Nacos 命名空间 */
    public static String NACOS_NAMESPACE;

    /** Nacos 配置的 Data ID */
    public static String NACOS_ROUTER_DATA_ID;

    /** Nacos 配置的 Group */
    public static String NACOS_ROUTER_GROUP;

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    public void setNacosServerAddr(String nacosServerAddr) {
        NACOS_SERVER_ADDR = nacosServerAddr;
    }

    @Value("${spring.cloud.nacos.discovery.namespace}")
    public void setNacosNamespace(String nacosNamespace) {
        NACOS_NAMESPACE= nacosNamespace;
    }

    @Value("${nacos.gateway.route.config.data-id}")
    public void setNacosRouterDataId(String nacosRouterDataId) {
        NACOS_ROUTER_DATA_ID= nacosRouterDataId;
    }

    @Value("${nacos.gateway.route.config.group}")
    public void setNacosRouterGroup(String nacosRouterGroup) {
        NACOS_ROUTER_GROUP = nacosRouterGroup;
    }

}
