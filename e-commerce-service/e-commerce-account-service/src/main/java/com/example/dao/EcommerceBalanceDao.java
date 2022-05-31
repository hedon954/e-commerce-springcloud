package com.example.dao;

import com.example.entity.EcommerceBalance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h1>EcommerceBalance 表 Dao 接口定义</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-31 4:50 PM
 */
public interface EcommerceBalanceDao extends JpaRepository<EcommerceBalance, Long> {

    /**
     * <h2>根据 userId 查询用户余额</h2>
     * @param userId 用户ID
     * @return 用户余额信息
     */
    EcommerceBalance findByUserId(Long userId);

}
