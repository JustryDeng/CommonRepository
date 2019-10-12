package com.szlaozicl.designpattern.strategy.duck;

import com.szlaozicl.designpattern.strategy.fly.FlyBehavior;
import com.szlaozicl.designpattern.strategy.quack.QuackBehavior;
import lombok.Getter;
import lombok.Setter;

/**
 * 父类鸭
 *
 * 注: 如果是在各个子类中直接实现FlyBehavior或QuackBehavior的话，那么子类的
 *     飞或叫行为就是定死了的。而如果将其作为一个属性的话，那么子类中可以通
 *     过setter等方式来指定其具体实现，从而达到切换子类能力的功能。
 *
 * @author JustryDeng
 * @date 2019/10/1 19:47
 */
public class Duck {

    /** 飞 属性 */
    @Setter
    @Getter
    protected FlyBehavior flyBehavior;

    /** 叫 属性 */
    @Setter
    @Getter
    protected QuackBehavior quackBehavior;

    /**
     * 所有鸭子都能游泳
     */
    public void swim() {
        System.out.println("鸭子游泳咯！");
    }

    /**
     * 所有鸭子都有外观
     */
    public void display() {
        System.out.println("鸭子外观 ma gia gia 的！");
    }

    /**
     * 调用flyBehavior的fly方法
     *
     * 即:这里将 飞 委托给了flyBehavior的具体实现
     */
    public void performFly() {
        flyBehavior.fly();
    }

    /**
     * 调用quackBehavior的quack方法
     *
     * 即:这里将 叫 委托给了quackBehavior的具体实现
     */
    public void performQuack() {
        quackBehavior.quack();
    }
}
