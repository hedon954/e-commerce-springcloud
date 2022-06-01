package com.example.service.impl;

import com.example.account.BalanceInfo;
import com.example.context.AccessContext;
import com.example.dao.EcommerceBalanceDao;
import com.example.entity.EcommerceBalance;
import com.example.service.IBalanceService;
import com.example.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <h1>用户余额服务接口实现</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-31 5:28 PM
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BalanceServiceImpl implements IBalanceService {

    private final EcommerceBalanceDao ecommerceBalanceDao;

    public BalanceServiceImpl(EcommerceBalanceDao ecommerceBalanceDao) {
        this.ecommerceBalanceDao = ecommerceBalanceDao;
    }

    @Override
    public BalanceInfo getCurrentUserBalanceInfo() {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        BalanceInfo balanceInfo = new BalanceInfo(loginUserInfo.getId(), 0L);
        EcommerceBalance ecommerceBalance = ecommerceBalanceDao.findByUserId(loginUserInfo.getId());

        if (null != ecommerceBalance) {
            balanceInfo.setBalance(ecommerceBalance.getBalance());
        } else {
            // 如果无用户余额记录，这里创建出来，余额为0
            EcommerceBalance nb = new EcommerceBalance();
            nb.setUserId(loginUserInfo.getId());
            nb.setBalance(0L);
            log.info("init user balance record: [{}]", ecommerceBalanceDao.save(nb).getId());
        }
        return balanceInfo;
    }

    @Override
    public BalanceInfo deductBalance(BalanceInfo balanceInfo) {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        /**
         * 扣减余额：
         * 1. 扣减额 <= 当前余额
         */
        EcommerceBalance ecommerceBalance = ecommerceBalanceDao.findByUserId(loginUserInfo.getId());
        if (null == ecommerceBalance ||
                ecommerceBalance.getBalance() - balanceInfo.getBalance() < 0) {
            throw new RuntimeException("user balance is not enough");
        }

        Long sourceBalance = ecommerceBalance.getBalance();
        ecommerceBalance.setBalance(ecommerceBalance.getBalance() - balanceInfo.getBalance());
        log.info("deductBalance | [{}], [{}], [{}]",
                ecommerceBalanceDao.save(ecommerceBalance).getId(),
                sourceBalance,
                balanceInfo.getBalance()
        );

        return new BalanceInfo(loginUserInfo.getId(), ecommerceBalance.getBalance());
    }
}
