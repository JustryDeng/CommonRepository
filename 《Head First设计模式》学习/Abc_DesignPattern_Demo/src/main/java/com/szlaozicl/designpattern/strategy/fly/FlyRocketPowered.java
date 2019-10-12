package com.szlaozicl.designpattern.strategy.fly;

/**
 * 火箭助力飞 实现
 *
 * @author JustryDeng
 * @date 2019/10/1 20:11
 */
public class FlyRocketPowered implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("火箭助力飞~");
    }
}
