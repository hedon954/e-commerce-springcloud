package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * <h1>Java8 Predicate 用法演示</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-25 11:11 AM
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PredicateTest {

    public static List<String> MICRO_SERVICE = Arrays.asList(
            "nacos", "authority", "gateway", "ribbon", "feign", "hystrix", "e-commerce"
    );

    /**
     * Predicate 中的 test() 方法主要用于判断参数符不符合规则，返回值是 boolean
     */
    @Test
    public void testPredicateTest() {
        Predicate<String> letterLengthLimit = s -> s.length() >5;
        MICRO_SERVICE.stream().filter(letterLengthLimit).forEach(System.out::println);
    }

    /**
     * Predicate 中的 and() 方法等同于逻辑与 &&，存在短路特性，需要所有条件都满足才可以
     */
    @Test
    public void testPredicateAnd() {
        Predicate<String> letterLengthLimit = s -> s.length() > 5;
        Predicate<String> letterStartWith = s -> s.startsWith("gate");

        MICRO_SERVICE.stream().filter(
                letterLengthLimit.and(letterStartWith)
        ).forEach(System.out::println);
    }

}
