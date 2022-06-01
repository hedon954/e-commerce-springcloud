package com.example.service;

import com.alibaba.fastjson.JSON;
import com.example.account.AddressInfo;
import com.example.common.TableId;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

/**
 * <h1>AddressService 测试类</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-31 5:30 PM
 */
@Slf4j
public class AddressServiceTest extends BaseTest{

    @Autowired
    IAddressService addressService;

    @Test
    public void createAddressInfo() {

        AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();
        addressItem.setUsername("hedon");
        addressItem.setPhone("18800000001");
        addressItem.setProvince("上海市");
        addressItem.setCity("上海市");
        addressItem.setAddressDetail("陆家嘴");

        log.info("test create address info: [{}]", JSON.toJSONString(
                addressService.createAddressInfo(
                        new AddressInfo(loginUserInfo.getId(),
                                Collections.singletonList(addressItem))
                )
        ));

    }

    @Test
    public void getCurrentAddressInfo() {
        log.info("test get current user info: [{}]", JSON.toJSONString(
                addressService.getCurrentAddressInfo()
        ));
    }

    @Test
    public void getAddressInfoById() {
        log.info("test get address info by id: [{}]", JSON.toJSONString(
                addressService.getAddressInfoById(10L)
        ));
    }

    @Test
    public void getAddressInfoByTableId() {
        log.info("test get address info by id: [{}]", JSON.toJSONString(
                addressService.getAddressInfoByTableId(
                        new TableId(Collections.singletonList(new TableId.Id(10L)))
                )
        ));
    }
}