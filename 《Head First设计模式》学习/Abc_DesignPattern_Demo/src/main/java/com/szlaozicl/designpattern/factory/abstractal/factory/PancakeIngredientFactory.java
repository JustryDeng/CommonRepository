package com.szlaozicl.designpattern.factory.abstractal.factory;

/**
 * 煎饼果子 众多原料的 抽象工厂
 *
 * @author JustryDeng
 * @date 2020/1/20 20:55
 */
public interface PancakeIngredientFactory {

    /**
     * 获取面团
     *
     * @return 面团
     */
    Object createDough();

    /**
     * 获取蔬菜
     *
     * @return 蔬菜
     */
    Object createVeggies();

    /**
     * 获取油条
     *
     * @return 油条
     */
    Object createFritters();

    /**
     * 获取腐乳
     *
     * @return 腐乳
     */
    Object createFermentedBeanCurd();

    /**
     * 获取葱花
     *
     * @return 葱花
     */
    Object createChoppedGreenOnion();
}