package com.example.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h1>用户账户余额信息</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-31 4:53 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户账户余额信息")
public class BalanceInfo implements Serializable {

    @ApiModelProperty(value = "用户主键 id")
    private Long userId;

    @ApiModelProperty(value = "用户账户余额")
    private Long balance;

}
