package com;

import com.demo.CacheEvictDemo;
import com.demo.CachePutDemo;
import com.demo.CacheableDemo;
import com.demo.CachingDemo;
import com.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NormalAnnotationTests {


    @Autowired
    CacheableDemo cacheableDemo;

    @Autowired
    CachePutDemo cachePutDemo;

    @Autowired
    CacheEvictDemo cacheEvictDemo;

    @Autowired
    CachingDemo cachingDemo;

    @Test
    public  void cacheableDemoTest() {
        User user = User.builder().name("张三").age(18).motto("蚂蚁牙黑！").build();
        System.out.println(cacheableDemo.fa("abc", user));
    }

    @Test
    public  void cachePutDemoTest() {
        Integer i = new Random().nextInt(10000);
        Integer res = cachePutDemo.fa(i);
        System.out.println(res);
    }

    @Test
    public  void cacheEvictDemoTest() {
        String res = cacheEvictDemo.fa("ppap");
        System.out.println(res);
    }

    @Test
    public  void cachingDemoTest() {
        Integer res = cachingDemo.fa(9527);
        System.out.println(res);
    }

}