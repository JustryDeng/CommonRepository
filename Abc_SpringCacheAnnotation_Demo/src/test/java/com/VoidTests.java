package com;

import com.demo.VoidDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VoidTests {

    @Autowired
    VoidDemo voidDemo;

    @Test
    public  void testVoidCache() {
        // 我们试着对返回值为void的方法进行缓存
        voidDemo.methodOne();
        // 获取该缓存
        Object obj = voidDemo.methodTwo();
        // 控制台输出结果为: 对返回值为void的方法进行缓存，缓存的value值为null
        System.out.println(obj== null ? "对返回值为void的方法进行缓存，缓存的value值为null" : obj);
    }

}