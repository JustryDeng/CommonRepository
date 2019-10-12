package com.szlaozicl.designpattern.strategy;

import com.szlaozicl.designpattern.strategy.duck.DecoyDuck;
import com.szlaozicl.designpattern.strategy.duck.Duck;
import com.szlaozicl.designpattern.strategy.fly.FlyWithWings;

/**
 * 测试 --- 策略模式
 *
 * @author JustryDeng
 * @date 2019/10/1 20:22
 */
public class Test {

    /**
     * 函数入口
     */
    public static void main(String[] args) {

        Duck duck = new DecoyDuck();
        // 运行方法，到这里时，控制台会输出: 火箭助力飞~
        duck.performFly();

        // 可以在程序中 切换行为
        // 注:如果是子类直接实现接口的话，是做不到这一点的
        duck.setFlyBehavior(new FlyWithWings());

        // 运行方法，到这里时，控制台会输出: 用翅膀飞~
        duck.performFly();
    }
}
