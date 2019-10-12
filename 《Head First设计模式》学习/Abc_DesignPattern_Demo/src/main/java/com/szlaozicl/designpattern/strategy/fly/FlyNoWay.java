package com.szlaozicl.designpattern.strategy.fly;

/**
 * 鸭子不能飞 的实现
 *
 * 注:不会飞，也可以看作是 飞的一种行为/方式
 *
 * @author JustryDeng
 * @date 2019/10/1 19:53
 */
public class FlyNoWay implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("什么都不做， 鸭子飞不了");
    }
}
