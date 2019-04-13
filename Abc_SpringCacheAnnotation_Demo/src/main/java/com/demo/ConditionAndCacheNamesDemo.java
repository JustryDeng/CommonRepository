package com.demo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 测试缓存注解的condition属性、测试多个cacheNames
 *
 * @author JustryDeng
 * @date 2019/4/13 11:14
 */
@Component
public class ConditionAndCacheNamesDemo {

    @Cacheable(cacheNames = "TestConditionSpace", key = "#p0")
    public String methodOne(String keyStr) {
        return "XYZ";
    }

    /**
     * condition作用时机在: 缓存注解检查缓存中是否有对应的key-value 之前
     *                    注:缓存注解检查缓存中是否有对应的key-value 在 运行目标方法之前，
     *                       所以 condition作用时机也在运行目标方法之前
     *
     * 【(若condition结果为真) && (指定cacheNames下存在对应key的缓存)】为真，那么就会从缓存中获取数据；
     *  否则就会执行方法，并将返回值作为key-value关系中的value，存入缓存
     */
    @Cacheable(cacheNames = "TestConditionSpace", key ="#str", condition = "#str.startsWith('abc')")
    public String methodTwo(String str) {
        System.out.println("说明【(若condition结果为真) && (指定cacheNames下存在对应key的缓存)】的结果为false!");
        return "methodTwo" + new Random().nextInt(10000);
    }


    /** ----------------------  下面三个方法用于测试 多个 cacheNames的情况 ---------------------------- */

    /**
     * 说明:本人将cacheName称呼为命名空间
     *
     * 注:若属性cacheNames(或属性value)指定了多个命名空间;
     *
     *       1.当进行缓存存储时，会在这些命名空间下都存一份key-value;
     *
     *       2.当进行缓存读取时，会按照cacheNames值里命名空间的顺序，挨个挨个从命名
     *         空间中查找对应的key，如果在某个命名空间中查找打了对应的缓存，就不会再
     *         查找排在后面的命名空间，也不会再执行对应方法，直接返回缓存中的value值
     *
     */
    @Cacheable(cacheNames = "TestConditionSpaceA", key = "'abcd'")
    public String methodA() {
        return "value-A";
    }

    @Cacheable(cacheNames = "TestConditionSpaceB", key = "'abcd'")
    public String methodB() {
        return "value-B";
    }

    @Cacheable(cacheNames = {"TestConditionSpaceB", "TestConditionSpaceA"}, key = "'abcd'")
    public String methodC() {
        System.out.println("说明(指定cacheNames下存在对应key的缓存)为false!");
        return "methodC";
    }
}
