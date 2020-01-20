package com.szlaozicl.designpattern.factory.methods.store;

import com.szlaozicl.designpattern.factory.methods.model.AbcCheesePizza;
import com.szlaozicl.designpattern.factory.methods.model.AbcDefaultPizza;
import com.szlaozicl.designpattern.factory.methods.model.AbcGreekPizza;
import com.szlaozicl.designpattern.factory.methods.model.AbcPepperoniPizza;
import com.szlaozicl.designpattern.factory.model.BasePizza;

/**
 * Abc国的披萨店(即:一种 工厂方法 的子类实现)
 *
 * @author JustryDeng
 * @date 2020/1/19 22:11
 */
public class AbcPizzaStore extends AbstractPizzaStore {

    @Override
    protected BasePizza createPizza(String type) {
        BasePizza pizza;
        if ("cheese".equals(type)) {
            pizza = new AbcCheesePizza();
        } else if ("greek".equals(type)) {
            pizza = new AbcGreekPizza();
        } else if ("pepperoni".equals(type)) {
            pizza = new AbcPepperoniPizza();
        } else {
            pizza = new AbcDefaultPizza();
        }
        return pizza;
    }
}
