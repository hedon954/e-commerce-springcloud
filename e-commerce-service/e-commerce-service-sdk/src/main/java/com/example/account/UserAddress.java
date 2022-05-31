package com.example.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Hedon Wang
 * @create 2022-05-31 4:29 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户地址基础信息")
public class UserAddress implements Serializable {

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "详细地址")
    private String addressDetail;
}
