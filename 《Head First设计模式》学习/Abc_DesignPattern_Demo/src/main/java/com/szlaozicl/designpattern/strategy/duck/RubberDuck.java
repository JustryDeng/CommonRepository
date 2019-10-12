package com.szlaozicl.designpattern.strategy.duck;

import com.szlaozicl.designpattern.strategy.fly.FlyNoWay;
import com.szlaozicl.designpattern.strategy.quack.MuteQuack;

/**
 * 橡皮鸭
 *
 * @author JustryDeng
 * @date 2019/10/1 20:05
 */
@SuppressWarnings("unused")
public class RubberDuck extends Duck {

    /**
     * 构造方法
     *
     * 注:在构造方法中指定初始化的 飞 和 叫 的行为实现
     */
    public RubberDuck() {
        // 橡皮鸭 既不能飞，又不能叫
        this.flyBehavior = new FlyNoWay();
        this.quackBehavior = new MuteQuack();
    }

}
