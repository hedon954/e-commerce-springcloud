package com.example.service;

import com.alibaba.fastjson.JSON;
import com.example.util.TokenParseUtil;
import com.example.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <h1>JWT 相关服务测试类</h1>
 * @author Hedon Wang
 * @create 2022-05-24 9:48 PM
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class JWTServiceTest {

    @Autowired
    private IJWTService ijwtService;

    @Test
    public void testGenerateAndParseToken() throws Exception {
        String token = ijwtService.generateToken("hedon", "25d55ad283aa400af464c76d713c07ad");
        log.info("jwt token is: [{}]", token);

        LoginUserInfo loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        log.info("login user info is: [{}]", JSON.toJSONString(loginUserInfo));
    }


}
