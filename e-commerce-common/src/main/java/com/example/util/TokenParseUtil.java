package com.example.util;

import com.alibaba.fastjson.JSON;
import com.example.constant.CommonConstant;
import com.example.vo.LoginUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;

/**
 * <h1>JWT Token 解析工具类</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-24 9:31 PM
 */
public class TokenParseUtil {

    /**
     * <h2>从 JWT 中解析 loginUserInfo 对象</h2>
     */
    public static LoginUserInfo parseUserInfoFromToken(String token) throws Exception {
        if (token == null) {
            return null;
        }

        // 获取 payload
        Jws<Claims> claimsJws = parseToken(token, getPublicKey());
        Claims body = claimsJws.getBody();

        // 判断 token 是否过期
        if (body.getExpiration().before(Calendar.getInstance().getTime())) {
            return null;
        }

        //返回 token 中的用户信息
        return JSON.parseObject(
                body.get(CommonConstant.JWT_USER_INFO_KEY).toString(),
                LoginUserInfo.class
        );
    }

    /**
     * <h2>根据本地存储的公钥获取 PublicKey 对象</h2>
     */
    private static PublicKey getPublicKey() throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(CommonConstant.PUBLIC_KEY)
        );
        return KeyFactory.getInstance(CommonConstant.RSA_ALGORITHM).generatePublic(keySpec);
    }


    /**
     * <h2>通过公钥去解析 JWT Token</h2>
     */
    private static Jws<Claims> parseToken(String token, PublicKey publicKey) throws Exception {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

}
