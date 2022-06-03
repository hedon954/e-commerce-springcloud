package com.example.convertor;

import com.example.constant.BrandCategory;

import javax.persistence.AttributeConverter;

/**
 * <h1>品牌分类枚举属性转换器</h1>
 *
 * @author Hedon Wang
 * @create 2022-06-03 2:40 PM
 */
public class BrandCategoryConvertor implements AttributeConverter<BrandCategory, String> {
    @Override
    public String convertToDatabaseColumn(BrandCategory brandCategory) {
        return brandCategory.getCode();
    }

    @Override
    public BrandCategory convertToEntityAttribute(String code) {
        return BrandCategory.of(code);
    }
}
