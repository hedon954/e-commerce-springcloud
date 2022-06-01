package com.example.conf;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <h1>Swagger 配置类</h1>
 * 原生：/swagger-ui.html
 * 美化：/doc.html
 *
 * @author Hedon Wang
 * @create 2022-05-30 5:51 PM
 */
@Configuration
@EnableSwagger2
@SuppressWarnings("all")
public class SwaggerConfig {


    /**
     * <h2>Swagger 实例 bean 是 Docket，通过配置 Docket 实例来配置 Swagger</h2>
     */
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())     //展示在 Swagger 页面上的自定义工程描述信息
                .select()               //选择展示哪些接口
                .apis(RequestHandlerSelectors.basePackage("com.example"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * <h2>Swagger 的描述信息</h2>
     */
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("imooc-micro-service")
                .description("e-commerce-springcloud-service")
                .contact(new Contact("Hedon", "https://hedon954.github.io/noteSite/", "171725713@qq.com"))
                .version("1.0")
                .build();
    }
}
