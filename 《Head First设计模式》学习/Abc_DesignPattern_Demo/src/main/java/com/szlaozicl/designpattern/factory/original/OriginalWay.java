package com.szlaozicl.designpattern.factory.original;

import com.szlaozicl.designpattern.factory.methods.model.*;
import com.szlaozicl.designpattern.factory.model.*;

/**
 * 原生方式(即:在不使用工厂模式的情况下，代码的样子)
 *
 * @author JustryDeng
 * @date 2020/1/19 21:53
 */
@SuppressWarnings("unused")
public class OriginalWay {


    public static void main(String[] args) {
        // 示例: 简单工厂/静态工厂 的原生实现
        simpleOrStatic("greek");
        // 示例: 工厂方法 的原生实现
        factoryMethodOriginal("Xyz", "greek");
    }

    /**
     * 不使用(简单工厂/静态工厂)的写法
     *
     * @param type
     *         披萨的类型
     * @date 2020/1/19 21:56
     */
    public static void simpleOrStatic(String type) {
        BasePizza pizza;
        // 第一步，获取到生的披萨
        if ("cheese".equals(type)) {
            pizza = new CheesePizza();
        } else if ("greek".equals(type)) {
            pizza = new GreekPizza();
        } else if ("pepperoni".equals(type)) {
            pizza = new PepperoniPizza();
        } else {
            pizza = new DefaultPizza();
        }

        // 第二步，将生披萨加工成熟披萨
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }

    /**
     * 不使用(工厂方法)的写法
     *
     * @param country
     *         国家
     * @param type
     *         披萨的类型
     * @date 2020/1/20 10:55
     */
    public static void factoryMethodOriginal(String country, String type) {
        BasePizza pizza;
        // 第一步，获取到生的披萨
        if ("Abc".equals(country)) {
            if ("cheese".equals(type)) {
                pizza = new AbcCheesePizza();
            } else if ("greek".equals(type)) {
                pizza = new AbcGreekPizza();
            } else if ("pepperoni".equals(type)) {
                pizza = new AbcPepperoniPizza();
            } else {
                pizza = new AbcDefaultPizza();
            }
        } else if ("Xyz".equals(country)) {
            if ("cheese".equals(type)) {
                pizza = new XyzCheesePizza();
            } else if ("greek".equals(type)) {
                pizza = new XyzGreekPizza();
            } else if ("pepperoni".equals(type)) {
                pizza = new XyzPepperoniPizza();
            } else {
                pizza = new XyzDefaultPizza();
            }
        } else {
            throw new RuntimeException("not support country [" + country + "]");
        }

        // 第二步，将生披萨加工成熟披萨
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }

}
