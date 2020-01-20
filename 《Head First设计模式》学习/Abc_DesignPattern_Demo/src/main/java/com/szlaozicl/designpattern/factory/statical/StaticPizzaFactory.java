package com.szlaozicl.designpattern.factory.statical;

import com.szlaozicl.designpattern.factory.model.*;

/**
 * 静态的披萨工厂
 *
 * @author JustryDeng
 * @date 2020/1/19 22:11
 */
public class StaticPizzaFactory {

    /**
     * 获取生披萨
     *
     * @param type
     *         披萨类型
     * @return 生披萨
     * @date 2020/1/19 22:13
     */
    public static BasePizza createPizza(String type) {
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
