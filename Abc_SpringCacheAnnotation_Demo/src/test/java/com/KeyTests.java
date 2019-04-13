package com;

import com.demo.KeyDemo;
import com.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KeyTests {


    @Autowired
    KeyDemo keyDemo;

    @Test
    public  void testOne() {
        keyDemo.methodOne();
    }

    @Test
    public  void testTwo() {
        keyDemo.methodTwo("paramA");
    }

    @Test
    public  void testThree() {
        User user = User.builder().name("张三").age(18).motto("蚂蚁牙黑！").build();
        keyDemo.methodThree(user);
    }


    @Test
    public  void testFour() {
        User user = User.builder().name("李四").age(18).motto("蚂蚁牙黑！").build();
        keyDemo.methodFour("paramA", 1, user);
    }

    @Test
    public  void testFive() {
        keyDemo.methodFive("paramA");
    }

    @Test
    public  void testSix() {
        keyDemo.methodSix();
    }

    @Test
    public  void testSeven() {
        keyDemo.methodSeven("ma~yi~ya~hei!");
    }

    @Test
    public  void testEight() {
        User user = User.builder().name("JustryDeng").age(18).motto("蚂蚁牙黑！").build();
        keyDemo.methodEight("123木头人", user);
    }


    @Test
    public  void testNine() {
        keyDemo.methodNine();
    }
}