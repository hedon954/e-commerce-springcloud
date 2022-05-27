package com.example.constant;

/**
 * <h1>Gateway 常量定义</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-26 3:09 PM
 */
public class GatewayConstant {

    /** 登录 URI */
    public static final String LOGIN_URI = "/e-commerce/login";

    /** 注册 URI */
    public static final String REGISTER_URI = "/e-commerce/register";

    /** 去授权中心拿到登录 token 的 URI 格式化接口 */
    public static final String AUTHORITY_CENTER_TOKEN_URL_FORMAT = "http://%s:%s/ecommerce-authority-center/authority/token";

    /** 去授权中心注册并拿到 token 的 URI 格式化接口 */
    public static final String AUTHORITY_CENTER_REGISTER_URL_FORMAT = "http://%s:%s/ecommerce-authority-center/authority/register";

}
