package com.example.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <h1>网关中需要注入到容器中的 bean</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-27 1:32 PM
 */
@Configuration
public class GatewayBeanConf {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
