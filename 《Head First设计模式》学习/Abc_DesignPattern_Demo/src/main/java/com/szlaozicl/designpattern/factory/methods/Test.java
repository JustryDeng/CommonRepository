package com.szlaozicl.designpattern.factory.methods;

import com.szlaozicl.designpattern.factory.methods.store.AbcPizzaStore;
import com.szlaozicl.designpattern.factory.methods.store.XyzPizzaStore;

/**
 * 工厂方法 使用测试
 *
 * @author JustryDeng
 * @date 2020/1/19 22:18
 */
public class Test {

    /**
     * main方法
     */
    public static void main(String[] args) {
        // Abc国的 芝士披萨
        AbcPizzaStore abcPizzaStore = new AbcPizzaStore();
        abcPizzaStore.orderPizza("cheese");

        System.out.println();

        // Xyz国的 芝士披萨
        XyzPizzaStore xyzPizzaStore = new XyzPizzaStore();
        xyzPizzaStore.orderPizza("cheese");
    }

}
