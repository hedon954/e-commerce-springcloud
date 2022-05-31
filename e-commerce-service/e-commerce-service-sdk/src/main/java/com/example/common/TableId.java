package com.example.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <h1>主键 ids</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-31 4:58 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "通用 ID 对象")
public class TableId implements Serializable {

    @ApiModelProperty(value = "数据表记录主键")
    private List<Id> ids;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "数据表记录主键对象")
    public static class Id{
        @ApiModelProperty(value = "数据表记录主键字段")
        private Long id;
    }

}
