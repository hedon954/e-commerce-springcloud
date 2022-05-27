package com.example.service;

import com.example.vo.UsernameAndPassword;

/**
 * <h1>JWT 相关服务接口定义</h1>
 * @author Hedon Wang
 * @create 2022-05-24 6:47 PM
 */
public interface IJWTService {

    /**
     * <h2>生成 JWT Token，使用默认的超时时间</h2>
     * @param username 用户名
     * @param password 密码
     * @return token
     * @throws Exception
     */
    String generateToken(String username, String password) throws Exception;

    /**
     * <h2>生成指定超时时间的 Token，单位是天</h2>
     * @param username 用户名
     * @param password 密码
     * @param expireDay 过期时间（天）
     * @return token
     * @throws Exception
     */
    String generateToken(String username, String password, int expireDay) throws Exception;

    /**
     * <h2>注册用户并生成 Token 返回</h2>
     * @param usernameAndPassword
     * @return token
     * @throws Exception
     */
    String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception;

}
