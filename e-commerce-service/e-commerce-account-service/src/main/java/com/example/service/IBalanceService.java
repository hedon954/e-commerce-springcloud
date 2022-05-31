package com.example.service;

import com.example.account.BalanceInfo;

/**
 * <h1>用户余额相关服务接口定义</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-31 5:05 PM
 */
public interface IBalanceService {

    /**
     * <h2>获取当前用户余额信息</h2>
     * @return 用户余额信息
     */
    BalanceInfo getCurrentUserBalanceInfo();

    /**
     * <h2>扣减用户余额</h2>
     * @param balanceInfo 代表想要扣减的余额
     * @return 扣后剩下的余额
     */
    BalanceInfo deductBalance(BalanceInfo balanceInfo);
}
