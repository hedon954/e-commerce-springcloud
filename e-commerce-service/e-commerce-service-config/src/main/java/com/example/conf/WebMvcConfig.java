package com.example.conf;

import cn.hutool.log.Log;
import com.example.interceptor.LoginUserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * <h1>Web Mvc 配置</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-30 5:46 PM
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * <h2>添加拦截器配置</h2>
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 添加用户身份统一登录拦截器
        registry.addInterceptor(new LoginUserInfoInterceptor())
                .addPathPatterns("/**").order(0);
    }

    /**
     * <h2>加载静态资源</h2>
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        super.addResourceHandlers(registry);
    }
}
