package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.constant.AuthorityConstant;
import com.example.constant.CommonConstant;
import com.example.dao.EcommerceUserDao;
import com.example.entity.EcommerceUser;
import com.example.service.IJWTService;
import com.example.vo.LoginUserInfo;
import com.example.vo.UsernameAndPassword;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.transaction.Transactional;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * <h1>JWT 相关服务接口实现</h1>
 * @author Hedon Wang
 * @create 2022-05-24 6:51 PM
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class JWTServiceImpl implements IJWTService {

    private final EcommerceUserDao ecommerceUserDao;

    public JWTServiceImpl(EcommerceUserDao ecommerceUserDao) {
        this.ecommerceUserDao = ecommerceUserDao;
    }

    @Override
    public String generateToken(String username, String password) throws Exception {
        return generateToken(username, password, 0);
    }

    @Override
    public String generateToken(String username, String password, int expireDay) throws Exception {

        // 1. 验证用户合法性
        EcommerceUser eu = ecommerceUserDao.findByUsernameAndPassword(username, password);
        if (eu == null){
            log.error("generateToken | can not find user: [{}], [{}]", username, password);
            return null;
        }

        // 2. Token 中塞入对象，即 payload
        LoginUserInfo lu = new LoginUserInfo(eu.getId(), eu.getUsername());

        // 3. 计算超时时间
        if (expireDay <= 0) {
            expireDay = AuthorityConstant.DEFAULT_EXPIRE_DAY;
        }
        ZonedDateTime zdt  = LocalDate.now().plus(expireDay, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());

        // 4. 生成 jwt
        return Jwts.builder()
                // payload --> kv
                .claim(CommonConstant.JWT_USER_INFO_KEY, JSON.toJSONString(lu))
                // jwt id --> 唯一性标志，无实际作用
                .setId(UUID.randomUUID().toString())
                // expire time --> 过期时间
                .setExpiration(expireDate)
                // signal --> 加密
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception {

        if (usernameAndPassword == null) {
            throw new Exception("registerUserAndGenerateToken | usernameAndPassword is null");
        }

        // 1. 用户唯一性判断
        EcommerceUser ou = ecommerceUserDao.findByUsername(usernameAndPassword.getUsername());
        if (ou != null) {
            log.error("registerUserAndGenerateToken | username is existed: [{}]", ou.getUsername());
            return null;
        }

        // 2. 创建用户记录
        EcommerceUser nu = new EcommerceUser();
        nu.setUsername(usernameAndPassword.getUsername());
        nu.setPassword(usernameAndPassword.getPassword()); // MD5
        nu.setExtraInfo("{}");

        // 3. 插入用户记录
        nu = ecommerceUserDao.save(nu);
        log.info("registerUserAndGenerateToken | register user successfully: [{}], [{}]", nu.getUsername(), nu.getId());

        // 4. 生成 token
        return generateToken(nu.getUsername(), nu.getPassword());
    }

    /**
     * <h2>根据本地存储的私钥获取到 PrivateKey 对象</h2>
     * @return PrivateKey 对象
     * @throws Exception
     */
    private PrivateKey getPrivateKey() throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(AuthorityConstant.PRIVATE_KEY)
        );
        KeyFactory keyFactory = KeyFactory.getInstance(CommonConstant.RSA_ALGORITHM);
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }
}
