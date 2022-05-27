package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h1>用户名和密码对象</h1>
 * @author Hedon Wang
 * @create 2022-05-24 5:45 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsernameAndPassword implements Serializable {

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

}
