package com.example.dao;

import com.example.entity.EcommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h1>Ecommerce Dao 接口定义</h1>
 * @author Hedon Wang
 * @create 2022-05-24 3:50 PM
 */
public interface EcommerceUserDao extends JpaRepository<EcommerceUser, Long> {

    /**
     * <h2>根据用户名查询 EcommerceUser 对象</h2>
     * select * from t_ecommerce_user where username=?;
     * @param username 用户名
     * @return
     */
    EcommerceUser findByUsername(String username);

    /**
     * <h2>根据用户名和密码查询 EcommerceUser 对象</h2>
     * select * from t_ecommerce_user where username=? and password=?;
     * @param username 用户名
     * @param password 密码
     * @return
     */
    EcommerceUser findByUsernameAndPassword(String username, String password);



}
