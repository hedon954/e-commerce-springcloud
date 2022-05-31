package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.example.account.AddressInfo;
import com.example.common.TableId;
import com.example.context.AccessContext;
import com.example.dao.EcommerceAddressDao;
import com.example.entity.EcommerceAddress;
import com.example.service.IAddressService;
import com.example.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>IAddressService 实现类</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-31 5:08 PM
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class AddressServiceImpl implements IAddressService {

    private final EcommerceAddressDao ecommerceAddressDao;

    public AddressServiceImpl(EcommerceAddressDao ecommerceAddressDao) {
        this.ecommerceAddressDao = ecommerceAddressDao;
    }

    /**
     * <h2>存储多个用户地址信息</h2>
     * @param addressInfo 用户地址信息vo，其中的 userId 不用（不安全）
     * @return 插入数据后生成的表ID
     */
    @Override
    public TableId createAddressInfo(AddressInfo addressInfo) {

        // 获取当前用户
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        // 将传递的参数转换为实体对象
        // TODO: 真实场景应该做数据校验
        List<EcommerceAddress> ecommerceAddresses = addressInfo.getAddressItems()
                .stream()
                .map(a -> EcommerceAddress.to(loginUserInfo.getId(), a))
                .collect(Collectors.toList());

        // 保存到数据表并把返回记录的 id 回给调用方
        List<EcommerceAddress> saveRecords = ecommerceAddressDao.saveAll(ecommerceAddresses);
        List<Long> ids = saveRecords.stream()
                .map(EcommerceAddress::getId)
                .collect(Collectors.toList());

        log.info("createAddressInfo | create address infos: [{}], [{}]", loginUserInfo.getId(), JSON.toJSONString(ids));

        return new TableId(ids.stream()
                .map(TableId.Id::new)
                .collect(Collectors.toList()));
    }

    @Override
    public AddressInfo getCurrentAddressInfo() {

        // 获取当前用户
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        // 查询地址信息
        List<EcommerceAddress> ecommerceAddresses = ecommerceAddressDao.findAllByUserId(loginUserInfo.getId());

        // 转换为 vo
        return new AddressInfo(
                loginUserInfo.getId(),
                ecommerceAddresses
                        .stream()
                        .map(EcommerceAddress::toAddressItem)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public AddressInfo getAddressInfoById(Long id) {

        EcommerceAddress ecommerceAddress = ecommerceAddressDao.findById(id).orElse(null);
        if (null == ecommerceAddress){
            throw new RuntimeException("address is not exist");
        }

        return new AddressInfo(
                ecommerceAddress.getUserId(),
                Collections.singletonList(ecommerceAddress.toAddressItem())
        );
    }

    @Override
    public AddressInfo getAddressInfoByTableId(TableId tableId) {

        List<Long> ids = tableId.getIds()
                .stream()
                .map(TableId.Id::getId)
                .collect(Collectors.toList());

        log.info("getAddressInfoByTableId | get address info by table id: [{}]", JSON.toJSONString(ids));

        List<EcommerceAddress> ecommerceAddresses = ecommerceAddressDao.findAllById(ids);
        if (CollectionUtils.isEmpty(ecommerceAddresses)) {
            return new AddressInfo(-1L, Collections.emptyList());
        }

        return new AddressInfo(
                ecommerceAddresses.get(0).getUserId(),
                ecommerceAddresses
                        .stream()
                        .map(EcommerceAddress::toAddressItem)
                        .collect(Collectors.toList())
        );
    }
}
