package com.szlaozicl.designpattern.factory.simple;

import com.szlaozicl.designpattern.factory.model.*;

/**
 * 简单的披萨工厂
 *
 * @author JustryDeng
 * @date 2020/1/19 22:11
 */
public class SimplePizzaFactory {

    /**
     * 获取生披萨
     *
     * @param type
     *         披萨类型
     * @return 生披萨
     * @date 2020/1/19 22:13
     */
    public BasePizza createPizza(String type) {
        BasePizza pizza;
        if ("cheese".equals(type)) {
            pizza = new CheesePizza();
        } else if ("greek".equals(type)) {
            pizza = new GreekPizza();
        } else if ("pepperoni".equals(type)) {
            pizza = new PepperoniPizza();
        } else {
            pizza = new DefaultPizza();
        }
        return pizza;
    }
}
