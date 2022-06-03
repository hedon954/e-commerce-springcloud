package com.example.convertor;


import com.example.constant.GoodsCategory;

import javax.persistence.AttributeConverter;

/**
 * <h1>商品类别枚举属性转换器</h1>
 *
 * @author Hedon Wang
 * @create 2022-06-03 2:40 PM
 * */
public class GoodsCategoryConvertor implements AttributeConverter<GoodsCategory, String> {

    @Override
    public String convertToDatabaseColumn(GoodsCategory goodsCategory) {
        return goodsCategory.getCode();
    }

    @Override
    public GoodsCategory convertToEntityAttribute(String code) {
        return GoodsCategory.of(code);
    }
}
