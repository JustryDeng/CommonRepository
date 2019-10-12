package com.szlaozicl.designpattern.strategy.quack;

/**
 * 不会叫 实现
 *
 * 注:不会叫 可以看作是 叫的一种行为/方式
 *
 * @author JustryDeng
 * @date 2019/10/1 19:57
 */
public class MuteQuack implements QuackBehavior {

    @Override
    public void quack() {
        System.out.println("什么都不做，不会叫~");
    }
}
