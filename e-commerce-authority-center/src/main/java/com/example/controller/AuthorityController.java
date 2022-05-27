package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.annotation.IgnoreResponseAdvice;
import com.example.service.IJWTService;
import com.example.vo.JwtToken;
import com.example.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>对外暴露的授权服务接口</h1>
 * @author Hedon Wang
 * @create 2022-05-24 7:15 PM
 */
@Slf4j
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    private final IJWTService ijwtService;

    @Autowired
    public AuthorityController(IJWTService ijwtService) {
        this.ijwtService = ijwtService;
    }

    /**
     * <h2>登录功能：从授权中心获取 token，且返回信息中没有统一响应的包装</h2>
     */
    @IgnoreResponseAdvice
    @PostMapping("/token")
    public JwtToken token(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
        log.info("token | request to get token with param: [{}]", JSON.toJSONString(usernameAndPassword));
        return new JwtToken(ijwtService.generateToken(
                usernameAndPassword.getUsername(),
                usernameAndPassword.getPassword()
        ));
    }

    /**
     * <h2>注册用户并返回当前注册用户的 token</h2>
     */
    @IgnoreResponseAdvice
    @PostMapping("/register")
    public JwtToken register(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
        log.info("register | register user with param: [{}]", JSON.toJSONString(usernameAndPassword));
        return new JwtToken(ijwtService.registerUserAndGenerateToken(usernameAndPassword));
    }

}
