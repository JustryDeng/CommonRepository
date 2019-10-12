package com.szlaozicl.designpattern.strategy.duck;

import com.szlaozicl.designpattern.strategy.quack.Squeak;
import com.szlaozicl.designpattern.strategy.fly.FlyWithWings;

/**
 * 红头鸭
 *
 * @author JustryDeng
 * @date 2019/10/1 20:05
 */
@SuppressWarnings("unused")
public class RedheadDuck extends Duck {

    /**
     * 构造方法
     *
     * 注:在构造方法中指定初始化的 飞 和 叫 的行为实现
     */
    public RedheadDuck() {
        // 红头鸭 能用翅膀飞，能吱吱叫
        this.flyBehavior = new FlyWithWings();
        this.quackBehavior = new Squeak();
    }

}
