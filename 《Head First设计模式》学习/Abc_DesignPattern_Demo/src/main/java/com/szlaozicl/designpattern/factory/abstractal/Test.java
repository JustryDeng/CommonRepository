package com.szlaozicl.designpattern.factory.abstractal;

import com.szlaozicl.designpattern.factory.abstractal.factory.AbcPancakeIngredientFactory;
import com.szlaozicl.designpattern.factory.abstractal.factory.XyzPancakeIngredientFactory;
import com.szlaozicl.designpattern.factory.abstractal.model.AbcPancake;
import com.szlaozicl.designpattern.factory.abstractal.model.BasePancake;
import com.szlaozicl.designpattern.factory.abstractal.model.XyzPancake;

/**
 * 抽象工厂 使用测试
 *
 * @author JustryDeng
 * @date 2020/1/20 22:34
 */
public class Test {

    /**
     * main方法
     */
    public static void main(String[] args) {
        System.err.println("煎饼果子 abcPancake");
        BasePancake abcPancake = new AbcPancake(new AbcPancakeIngredientFactory());
        abcPancake.initIngredient();
        abcPancake.orderPancake();

        System.out.println();

        System.err.println("煎饼果子 xyzPancake");
        BasePancake xyzPancake = new XyzPancake(new XyzPancakeIngredientFactory());
        xyzPancake.initIngredient();
        xyzPancake.orderPancake();
    }
}
