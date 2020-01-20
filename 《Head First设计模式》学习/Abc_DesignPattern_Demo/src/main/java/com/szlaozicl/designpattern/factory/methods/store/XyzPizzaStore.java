package com.szlaozicl.designpattern.factory.methods.store;

import com.szlaozicl.designpattern.factory.methods.model.XyzCheesePizza;
import com.szlaozicl.designpattern.factory.methods.model.XyzDefaultPizza;
import com.szlaozicl.designpattern.factory.methods.model.XyzGreekPizza;
import com.szlaozicl.designpattern.factory.methods.model.XyzPepperoniPizza;
import com.szlaozicl.designpattern.factory.model.BasePizza;

/**
 * Xyz国的披萨店(即:一种 工厂方法 的子类实现)
 *
 * @author JustryDeng
 * @date 2020/1/19 22:11
 */
public class XyzPizzaStore extends AbstractPizzaStore {

    @Override
    protected BasePizza createPizza(String type) {
        BasePizza pizza;
        if ("cheese".equals(type)) {
            pizza = new XyzCheesePizza();
        } else if ("greek".equals(type)) {
            pizza = new XyzGreekPizza();
        } else if ("pepperoni".equals(type)) {
            pizza = new XyzPepperoniPizza();
        } else {
            pizza = new XyzDefaultPizza();
        }
        return pizza;
    }
}
