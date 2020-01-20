package com.szlaozicl.designpattern.factory.statical;

import com.szlaozicl.designpattern.factory.model.BasePizza;

/**
 * 静态工厂 使用测试
 *
 * @author JustryDeng
 * @date 2020/1/19 22:18
 */
public class Test {

    /**
     * main方法
     */
    public static void main(String[] args) {

        //  第一步，利用StaticPizzaFactory，获取到生的披萨
        BasePizza pizza = StaticPizzaFactory.createPizza("pepperoni");

        // 第二步，将生披萨加工成熟披萨
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }

}
