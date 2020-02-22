package com.szlaozicl.designpattern.templatemethod.usetemplate;

import com.szlaozicl.designpattern.templatemethod.Beverage;

/**
 * 模板方法模式下的 Coffee和Tea 制作测试
 *
 * @author JustryDeng
 * @date 2020/2/22 17:40:23
 */
public class Test {

    public static void main(String[] args) {
        Beverage coffeeBeverage = new Coffee();
        coffeeBeverage.prepareRecipe();

        System.out.println("----");

        Beverage teaBeverage = new Tea();
        teaBeverage.prepareRecipe();
    }
}
