package com.szlaozicl.designpattern.strategy.duck;

import com.szlaozicl.designpattern.strategy.fly.FlyRocketPowered;
import com.szlaozicl.designpattern.strategy.quack.Quack;

/**
 * 诱饵鸭
 *
 * @author JustryDeng
 * @date 2019/10/1 20:05
 */
@SuppressWarnings("unused")
public class DecoyDuck extends Duck {

    /**
     * 构造方法
     *
     * 注:在构造方法中指定初始化的 飞 和 叫 的行为实现
     */
    public DecoyDuck() {
        // 诱饵鸭  能火箭助力飞， 能呱呱叫
        this.flyBehavior = new FlyRocketPowered();
        this.quackBehavior = new Quack();
    }

    /// other....
}
