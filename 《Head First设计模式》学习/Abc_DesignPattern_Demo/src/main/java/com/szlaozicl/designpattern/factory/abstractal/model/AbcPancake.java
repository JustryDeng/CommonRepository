package com.szlaozicl.designpattern.factory.abstractal.model;

import com.szlaozicl.designpattern.factory.abstractal.factory.PancakeIngredientFactory;

/**
 * Abc省的 煎饼果子
 *
 * @author JustryDeng
 * @date 2020/1/20 21:22
 */
public class AbcPancake extends BasePancake {

    /** 原料工厂 */
    PancakeIngredientFactory pancakeIngredientFactory;

    public AbcPancake(PancakeIngredientFactory pancakeIngredientFactory) {
        this.pancakeIngredientFactory = pancakeIngredientFactory;
    }

    /**
     * 利用抽象工厂PancakeIngredientFactory，来获取实际的原料
     */
    @Override
    public void initIngredient() {
        choppedGreenOnion = pancakeIngredientFactory.createChoppedGreenOnion();
        dough = pancakeIngredientFactory.createDough();
        veggies = pancakeIngredientFactory.createVeggies();
        fritters = pancakeIngredientFactory.createFritters();
        fermentedBeanCurd = pancakeIngredientFactory.createFermentedBeanCurd();
    }
}
