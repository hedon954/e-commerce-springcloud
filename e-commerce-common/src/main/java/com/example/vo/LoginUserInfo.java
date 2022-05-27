package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h1>登录用户信息</h1>
 * @author Hedon Wang
 * @create 2022-05-24 5:47 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserInfo implements Serializable {

    /** 用户表主键 ID */
    private Long id;

    /** 用户名 */
    private String username;
}
