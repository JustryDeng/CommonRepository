package com.szlaozicl.designpattern.factory.model;

/**
 * 经典(默认)的披萨
 *
 * @author JustryDeng
 * @date 2020/1/19 21:59
 */
public class DefaultPizza extends BasePizza {

    @Override
    public String name() {
        return "经典(默认)的披萨";
    }
}
