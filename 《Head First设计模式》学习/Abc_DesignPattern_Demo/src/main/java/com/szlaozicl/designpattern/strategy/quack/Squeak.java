package com.szlaozicl.designpattern.strategy.quack;

/**
 * 吱吱叫 实现
 *
 * @author JustryDeng
 * @date 2019/10/1 19:56
 */
public class Squeak implements QuackBehavior {

    @Override
    public void quack() {
        System.out.println("实现吱吱叫~");
    }
}
