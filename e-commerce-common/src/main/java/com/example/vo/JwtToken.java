package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h1>授权中心鉴权之后给客户端的 Token</h1>
 * @author Hedon Wang
 * @create 2022-05-24 5:46 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken implements Serializable {

    /** JWT */
    private String token;
}
