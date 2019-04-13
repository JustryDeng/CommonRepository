package com;

import com.demo.ConditionAndCacheNamesDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConditionTests {


    @Autowired
    ConditionAndCacheNamesDemo demo;

    /**
     * methodOne无condition条件
     *
     * methodTwo需要验证condition条件
     *
     * @date 2019/4/13 11:55
     */
    @Test
    public  void testVoidCacheOne() {
        // -------------------------> 测试condition不成立的情况
        // 向缓存中 存入 key为 'xyz',(返回)值为'XYZ'的键值对缓存
        demo.methodOne("xyz");
        // 尝试从缓存中读取key为'xyz'的缓存
        String str1 = demo.methodTwo("xyz");
        // 输出结果为“methodTwo3448”
        // 说明 没从缓存中进行读取， 这是因为键'xyz'不满足condition属性条件
        System.out.println(str1);

        System.out.println("*************分割线*************");

        // -------------------------> 测试condition成立的情况
        // 向缓存中 存入 key为 'abcdefg',(返回)值为'XYZ'的键值对缓存
        demo.methodOne("abcdefg");
        // 尝试从缓存中读取key为'abcdefg'的缓存
        String str2 = demo.methodTwo("abcdefg");
        // 输出结果为“XYZ”
        // 说明 从缓存中进行数据读取了， 这是因为: 【(若condition结果为真) && (指定cacheNames下存在对应key的缓存)】结果为true
        System.out.println(str2);
    }


    @Test
    public  void testVoidCacheABC() {
        // 我那个命名空间TestConditionSpaceA中存入key为'abcd',值为'value-A'的数据
        demo.methodA();
        // 我那个命名空间TestConditionSpaceB中存入key为'abcd',值为'value-B'的数据
        demo.methodB();
        // methodC方法上的缓存注解如下：
        // @Cacheable(cacheNames = {"TestConditionSpaceB", "TestConditionSpaceA"}, key = "'abcd'")
        String str = demo.methodC();
        // 输出结果为 value-B
        System.out.println(str);
    }

}