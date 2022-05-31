package com.example.service.impl;

import com.example.account.BalanceInfo;
import com.example.service.IBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Hedon Wang
 * @create 2022-05-31 5:28 PM
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class BalanceServiceImpl implements IBalanceService {


    @Override
    public BalanceInfo getCurrentUserBalanceInfo() {
        return null;
    }

    @Override
    public BalanceInfo deductBalance(BalanceInfo balanceInfo) {
        return null;
    }
}
