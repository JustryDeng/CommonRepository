package com.szlaozicl.designpattern.adapter.classadapter;

import com.szlaozicl.designpattern.adapter.Duck;

/**
 * 测试 类适配
 *
 * @author JustryDeng
 * @date 2020/2/21 14:56:53
 */
public class Test {

    public static void main(String[] args) {
        /*
         * 1.系统需要的是Duck
         * 2.但是厂商提供的是WildTurkey
         * 3.编写一个适配器, 继承WildTurkey同时实现Duck,该适配器在实现Duck的方法时,
         *    内部其实调用的是父类WildTurkey的相关逻辑，并进行相关封装。
         */
        Duck duck = new WildTurkeyToDuckAdapter();
        duck.quack();
        duck.fly();
    }
}
