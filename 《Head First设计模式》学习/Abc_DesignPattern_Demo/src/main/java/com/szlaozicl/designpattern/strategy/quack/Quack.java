package com.szlaozicl.designpattern.strategy.quack;

/**
 * 呱呱叫 实现
 *
 * @author JustryDeng
 * @date 2019/10/1 19:55
 */
public class Quack implements QuackBehavior {

    @Override
    public void quack() {
        System.out.println("实现呱呱叫~");
    }
}
