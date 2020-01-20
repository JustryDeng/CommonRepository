package com.szlaozicl.designpattern.factory.simple;

import com.szlaozicl.designpattern.factory.model.BasePizza;

/**
 * 简单工厂 使用测试
 *
 * @author JustryDeng
 * @date 2020/1/19 22:18
 */
public class Test {

    /**
     * main方法
     */
    public static void main(String[] args) {

        SimplePizzaFactory simplePizzaFactory = new SimplePizzaFactory();

        //  第一步，利用SimplePizzaFactory实例，获取到生的披萨
        BasePizza pizza = simplePizzaFactory.createPizza("cheese");

        // 第二步，将生披萨加工成熟披萨
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }

}
