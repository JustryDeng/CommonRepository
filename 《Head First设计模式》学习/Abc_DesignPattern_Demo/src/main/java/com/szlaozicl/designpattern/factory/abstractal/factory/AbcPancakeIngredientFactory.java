package com.szlaozicl.designpattern.factory.abstractal.factory;

/**
 * Abc省对 抽象工厂PancakeIngredientFactory 的实现
 *
 * @author JustryDeng
 * @date 2020/1/20 21:38
 */
public class AbcPancakeIngredientFactory implements PancakeIngredientFactory {

    @Override
    public Object createDough() {
        return "Abc省 特有的面团";
    }

    @Override
    public Object createVeggies() {
        return "Abc省 特有的蔬菜";
    }

    @Override
    public Object createFritters() {
        return "Abc省 特有的油条";
    }

    @Override
    public Object createFermentedBeanCurd() {
        return "Abc省 特有的腐乳";
    }

    @Override
    public Object createChoppedGreenOnion() {
        return "Abc省 特有的葱花";
    }
}