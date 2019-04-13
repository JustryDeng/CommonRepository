package com;

import com.demo.UnlessDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnlessTests {


    @Autowired
    UnlessDemo demo;

    /**
     * methodOne无condition条件
     *
     * methodTwo需要验证condition条件
     *
     * @date 2019/4/13 11:55
     */
    @Test
    public  void testVoidCacheOne() {
        Integer i = new Random().nextInt(10000);
        Integer res = demo.methodTwo(i);
        System.out.println(res);
    }

}