package com.szlaozicl.designpattern.factory.methods.model;

import com.szlaozicl.designpattern.factory.model.BasePizza;

/**
 * Xyz国出售的 意大利辣香肠披萨
 *
 * @author JustryDeng
 * @date 2020/1/19 21:59
 */
public class XyzPepperoniPizza extends BasePizza {

    @Override
    public String name() {
        return "Xyz国出售的 意大利辣香肠披萨";
    }
}
