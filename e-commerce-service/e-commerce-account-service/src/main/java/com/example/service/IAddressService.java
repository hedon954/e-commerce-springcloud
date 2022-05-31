package com.example.service;

import com.example.account.AddressInfo;
import com.example.common.TableId;

/**
 * <h1>用户地址相关服务接口定义</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-31 4:57 PM
 */
public interface IAddressService {

    /**
     * <h2>创建用户地址信息</h2>
     * @param addressInfo 用户地址信息vo
     * @return 插入数据库后自动生成的 ID
     */
    TableId createAddressInfo(AddressInfo addressInfo);

    /**
     * <h2>获取当前用户地址信息</h2>
     * @return 用户地址信息
     */
    AddressInfo getCurrentAddressInfo();

    /**
     * <h2>通过 ID 获取用户地址信息</h2>
     * @param id 表ID
     * @return 用户地址信息
     */
    AddressInfo getAddressInfoById(Long id);

    /**
     * <h2>通过 TableId 获取用户地址信息（可以批量）</h2>
     * @param tableId 表 Id 封装对象
     * @return 用户地址信息（可批量）
     */
    AddressInfo getAddressInfoByTableId(TableId tableId);
}
