package com.szlaozicl.designpattern.strategy.fly;

/**
 * 用翅膀飞
 *
 * @author JustryDeng
 * @date 2019/10/1 19:52
 */
public class FlyWithWings implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("用翅膀飞~");
    }

}
