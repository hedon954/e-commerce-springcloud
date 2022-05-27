package com.example.conf;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * <h1>配置安全认证，以便其他的微服务可以注册</h1>
 *
 * 参考 spring security 官网
 * @author Hedon Wang
 * @create 2022-05-23 4:50 PM
 */
@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {

    /** 应用上下文路径 */
    private final String adminContextPath;
    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    /**
     * <h2>配置安全认证</h2>
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminContextPath+"/");

        http.authorizeRequests()
                // 1. 配置所有的静态资源和登录页可以公开访问
                .antMatchers(adminContextPath + "/assets/**").permitAll()
                .antMatchers(adminContextPath +"/login").permitAll()
                // 2. 其他请求，必须要经过认证
                .anyRequest().authenticated()
                .and()
                // 3. 配置登录路径和登录成功处理器
                .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler)
                .and()
                // 4. 配置登出路径
                .logout().logoutUrl(adminContextPath + "/logout")
                .and()
                // 5. 开启 http basic 支持，其他的服务模块注册时需要使用呢
                .httpBasic()
                .and()
                // 6. 开启基于 cookie 的 csrf 保护
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                // 7. 忽略这些路径的 csrf 保护，以便其他模块可以实现注册
                .ignoringAntMatchers(
                        adminContextPath + "/instances",
                        adminContextPath + "/actuator/**"
                );
    }
}
