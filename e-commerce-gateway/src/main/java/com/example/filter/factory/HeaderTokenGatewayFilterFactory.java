package com.example.filter.factory;

import com.example.filter.HeaderTokenGatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * <h1>自定义的 Header Token 局部过滤器</h1>
 * 网关默认取过滤器名字规则：xxx - GatewayFilterFactory
 * 故下面 HeaderTokenGatewayFilterFactory 的过滤器名字为 HeaderToken
 *
 * @author Hedon Wang
 * @create 2022-05-26 10:21 AM
 */
@Component
public class HeaderTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return new HeaderTokenGatewayFilter();
    }
}
