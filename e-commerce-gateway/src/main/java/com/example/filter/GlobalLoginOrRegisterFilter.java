package com.example.filter;

import com.alibaba.fastjson.JSON;
import com.example.constant.CommonConstant;
import com.example.constant.GatewayConstant;
import com.example.util.TokenParseUtil;
import com.example.vo.JwtToken;
import com.example.vo.LoginUserInfo;
import com.example.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <h1>全局登录鉴权过滤器</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-27 1:25 PM
 */
@Slf4j
@Component
public class GlobalLoginOrRegisterFilter implements GlobalFilter, Ordered {

    private final LoadBalancerClient loadBalancerClient;
    private final RestTemplate restTemplate;

    public GlobalLoginOrRegisterFilter(RestTemplate restTemplate, LoadBalancerClient loadBalancerClient) {
        this.restTemplate = restTemplate;
        this.loadBalancerClient = loadBalancerClient;
    }


    /**
     * <h2>登录、注册、鉴权</h2>
     * 1. 如果是登录或注册，则去授权中心拿到 token 并返回给客户端
     * 2. 如果是访问其他服务，则鉴权，没有权限则返回 401
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 1. 登录请求
        if (request.getURI().getPath().contains(GatewayConstant.LOGIN_URI)) {
            // 去授权中心拿 token
            String token = getTokenFromAuthorityCenter(request, GatewayConstant.AUTHORITY_CENTER_TOKEN_URL_FORMAT);
            // header 中不能设置 null
            response.getHeaders().add(CommonConstant.JWT_USER_INFO_KEY, null == token ? "null": token);
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }

        // 2. 注册请求
        if (request.getURI().getPath().contains(GatewayConstant.REGISTER_URI)) {
            // 去授权中心拿 token
            String token = getTokenFromAuthorityCenter(request, GatewayConstant.AUTHORITY_CENTER_REGISTER_URL_FORMAT);
            // header 中不能设置 null
            response.getHeaders().add(CommonConstant.JWT_USER_INFO_KEY, null == token ? "null": token);
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }

        // 3. 其他请求 --> 进行鉴权，校验是否能够从 Token 中解析出用户信息
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(CommonConstant.JWT_USER_INFO_KEY);
        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        }catch (Exception e) {
            log.error("parse userinfo from token error: [{}]", e.getMessage(), e);
        }
        if (null == loginUserInfo) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 2;
    }

    /**
     * <h2><从授权中心获取 Token/h2>
     */
    private String getTokenFromAuthorityCenter(ServerHttpRequest request, String uriFormat) {

        // 1. 负载均衡获取服务实例
        ServiceInstance serviceInstance = loadBalancerClient.choose(CommonConstant.AUTHORITY_CENTER_SERVICE_ID);
        log.info("getTokenFromAuthorityCenter | nacos client info: [{}], [{}], [{}]",
                serviceInstance.getServiceId(),
                serviceInstance.getInstanceId(),
                JSON.toJSONString(serviceInstance.getMetadata())
        );

        // 2. 构建 request url
        String requestUrl = String.format(uriFormat, serviceInstance.getHost(), serviceInstance.getPort());

        // 3. 获取 request body 中的用户信息
        UsernameAndPassword usernameAndPassword = JSON.parseObject(parseBodyFromRequest(request), UsernameAndPassword.class);
        log.info("getTokenFromAuthorityCenter | login or register request url and body: [{}], [{}]",
                requestUrl,
                JSON.toJSONString(usernameAndPassword)
        );

        // 4. 发送请求去获取 token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        JwtToken jwtToken = restTemplate.postForObject(
                requestUrl,
                new HttpEntity<>(JSON.toJSONString(usernameAndPassword), httpHeaders),
                JwtToken.class);

        return null == jwtToken ? null : jwtToken.getToken();
    }

    /**
     * <h2>从 POST 请求中获取到请求数据</h2>
     */
    private String parseBodyFromRequest(ServerHttpRequest request){
        // 获取请求体
        Flux<DataBuffer> body = request.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();

        // 订阅缓冲区去消费请求体中的数据
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            // 全面用 DataBufferUtils.retain 维持了 DataBuffer
            // 拿到数据后必须要用 DataBufferUtils.release 去释放 DataBuffer
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });

        // 获取 request body
        return bodyRef.get();
    }
}
