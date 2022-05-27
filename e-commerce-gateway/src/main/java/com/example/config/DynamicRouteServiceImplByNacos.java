package com.example.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.common.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * <h1>通过 Nacos 下发动态路由配置，监听 Nacos 中路由配置变更</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-25 2:49 PM
 */
@Slf4j
@Service
@DependsOn({"gatewayConfig"}) // 指定的 bean 初始化后再来初始该 bean
public class DynamicRouteServiceImplByNacos {

    /** Nacos 配置服务 */
    private ConfigService configService;

    private final DynamicRouteServiceImpl dynamicRouteService;

    public DynamicRouteServiceImplByNacos(DynamicRouteServiceImpl dynamicRouteService) {
        this.dynamicRouteService = dynamicRouteService;
    }

    /**
     * <h2>bean 在容器中构造完成后会立即执行该方法</h2>
     */
    @PostConstruct
    public void init(){
        log.info("gateway route init...");
        try {
            // 1. 初始化配置服务服务器
            this.configService = initConfigService();
            if (configService == null) {
                log.error("init config service failed");
                return;
            }

            // 2. 通过 Nacos Config 并指定路由配置路径去获取路由配置
            String configInfo = configService.getConfig(
                    GatewayConfig.NACOS_ROUTER_DATA_ID,
                    GatewayConfig.NACOS_ROUTER_GROUP,
                    GatewayConfig.DEFAULT_TIMEOUT_MS
            );
            log.info("get current gateway config: [{}]", configInfo);

            // 3. 添加路由配置信息
            List<RouteDefinition> routeDefinitions = JSON.parseArray(configInfo, RouteDefinition.class);
            if (CollectionUtils.isNotEmpty(routeDefinitions)) {
                for (RouteDefinition routeDefinition : routeDefinitions) {
                    log.info("init gateway config: [{}]", routeDefinition);
                    dynamicRouteService.addRouteDefinition(routeDefinition);
                }
            }

        }catch (Exception e){
            log.error("gateway route init error: [{}]", e.getMessage(), e);
        }

        // 4. 添加监听器，实时监控并更新配置
        dynamicRouteByNacosListener(GatewayConfig.NACOS_ROUTER_DATA_ID, GatewayConfig.NACOS_ROUTER_GROUP);
    }

    /**
     * <h2>初始化 Nacos Config</h2>
     */
    private ConfigService initConfigService() {
        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr", GatewayConfig.NACOS_SERVER_ADDR);
            properties.setProperty("namespace", GatewayConfig.NACOS_NAMESPACE);
            return configService = NacosFactory.createConfigService(properties);
        }catch (Exception e){
            log.error("initConfigService | init gateway nacos config error: [{}]",e.getMessage(), e);
            return null;
        }
    }

    /**
     * <h2>监听 Nacos 下发的动态路由配置</h2>
     */
    private void dynamicRouteByNacosListener(String dataId, String group) {
        try {
            // 给 Nacos Config 客户端添加一个监听器
            configService.addListener(dataId, group, new Listener() {
                /**
                 * <h3>自己提供线程池去执行操作，return null 表示使用默认线程池</h3>
                 */
                @Override
                public Executor getExecutor() {
                    return null;
                }

                /**
                 * <h3>监听器收到配置更新</h3>
                 * @param configInfo Nacos 中最新的配置定义
                 */
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("dynamicRouteByNacosListener | start to update config: [{}]", configInfo);
                    List<RouteDefinition> routeDefinitions = JSON.parseArray(configInfo, RouteDefinition.class);
                    log.info("dynamicRouteByNacosListener | update route: [{}]", routeDefinitions.toString());
                    dynamicRouteService.updateRouteDefinitionList(routeDefinitions);
                }
            });
        }catch (NacosException e){
            log.error("dynamicRouteByNacosListener | dynamic update gateway config error: [{}]", e.getMessage(), e);
        }
    }
}
