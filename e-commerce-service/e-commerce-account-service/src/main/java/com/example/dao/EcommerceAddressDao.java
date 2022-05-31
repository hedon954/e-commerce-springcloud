package com.example.dao;

import com.example.entity.EcommerceAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <h1>EcommerceAddressDao 接口定义</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-31 4:43 PM
 */
public interface EcommerceAddressDao extends JpaRepository<EcommerceAddress, Long> {

    /**
     * <h2>根据用户 ID 查询地址信息</h2>
     * @param userId 用户ID
     * @return 用户地址列表
     */
    List<EcommerceAddress> findAllByUserId(Long userId);
}
