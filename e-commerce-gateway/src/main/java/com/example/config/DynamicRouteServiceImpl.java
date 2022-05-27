package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <h1>事件推送 Aware：动态更新路由网关 Service</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-25 2:25 PM
 */
@Slf4j
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    /** 写路由定义的工具 */
    private final RouteDefinitionWriter routeDefinitionWriter;

    /** 获取路由定义的工具 */
    private final RouteDefinitionLocator routeDefinitionLocator;

    /** 事件发布工具 */
    private ApplicationEventPublisher publisher;

    public DynamicRouteServiceImpl(RouteDefinitionWriter routeDefinitionWriter, RouteDefinitionLocator routeDefinitionLocator){
        this.routeDefinitionWriter = routeDefinitionWriter;
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        // 完成事件推送句柄初始化
        this.publisher = applicationEventPublisher;
    }

    /**
     * <h2>增加路由定义</h2>
     */
    public String addRouteDefinition(RouteDefinition routeDefinition) {

        log.info("addRouteDefinition | gateway add route: [{}]", routeDefinition);

        // 保存路由配置并发布
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        // 发布事件通知给 Gateway，同步新增的路由定义
        this.publisher.publishEvent(new RefreshRoutesEvent(this));

        return "success";
    }

    /**
     * <h2>更新路由</h2>
     * @return
     */
    public String updateRouteDefinitionList(List<RouteDefinition> routeDefinitions) {
        log.info("updateRouteDefinitionList | gateway update routes: [{}]", routeDefinitions);

        // 先拿到当前 Gateway 中存储的路由定义
        List<RouteDefinition> oldRouteDefinitions = this.routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
        if (!CollectionUtils.isEmpty(oldRouteDefinitions)) {
            // 清除掉之前所有的"旧的"路由定义
            oldRouteDefinitions.forEach( rd -> {
                log.info("updateRouteDefinitionList | delete route definition: [{}]", rd);
                deleteById(rd.getId());
            });
        }

        // 把更新的路由定义同步到 Gateway 中
        routeDefinitions.forEach(this::updateByRouteDefinition);

        return "success";
    }

    /**
     * <h2>通过 id 删除路由配置</h2>
     */
    private String deleteById(String id) {
        try {
            log.info("deleteById | gateway delete route id: [{}]", id);
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "delete success";
        }catch (Exception e){
            log.error("deleteById | gateway delete route failed: [{}]", e.getMessage(), e);
            return "delete failed";
        }
    }

    /**
     * <h2>更新路由</h2>
     * <p>更新的实现策略：删除 + 新增 = 更新</p>
     */
    private String updateByRouteDefinition(RouteDefinition routeDefinition) {
        try{
            log.info("updateByRouteDefinition | gateway update route: [{}]", routeDefinition);
            this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
        }catch (Exception e){
            return "update failed, not found route routeId: " + routeDefinition.getId();
        }

        try{
            this.routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        }catch (Exception e){
            return "update route failed";
        }
    }
}
