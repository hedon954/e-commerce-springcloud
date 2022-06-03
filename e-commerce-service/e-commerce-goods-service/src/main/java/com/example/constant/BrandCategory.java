package com.example.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * <h1>品牌分类</h1>
 *
 * @author Hedon Wang
 * @create 2022-06-03 2:36 PM
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("all")
public enum BrandCategory {

    BRAND_A("20001", "品牌 A"),
    BRAND_B("20002", "品牌 B"),
    BRAND_C("20003", "品牌 C"),
    BRAND_D("20004", "品牌 D"),
    BRAND_E("20005", "品牌 E"),
    ;

    /** 品牌分类编码 */
    private final String code;

    /** 品牌分类描述信息 */
    private final String description;

    /**
     * <h2>根据 code 获取到 BrandCategory</h2>
     */
    public static BrandCategory of(String code){
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter( bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException(code + " not exists")
                );
    }
}
