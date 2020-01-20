package com.szlaozicl.designpattern.factory.abstractal.model;

/**
 * 煎饼果子
 *
 * 提示:为了快速示例，这里所有的原料的类型，都采用的是Object；在实际开发中，需
 *      要根据实际情况，来决定是否创建对应的类。
 *
 * @author JustryDeng
 * @date 2020/1/20 21:09
 */
public abstract class BasePancake {

    /** 面团 */
    protected Object dough;

    /** 蔬菜 */
    protected Object veggies;

    /** 油条 */
    protected Object fritters;

    /** 腐乳 */
    protected Object fermentedBeanCurd;

    /** 葱花 */
    protected Object choppedGreenOnion;

    /**
     * 初始化 原料 (即:给上面的参数赋值)
     */
    public abstract void initIngredient();

    /**
     * 定制煎饼果子
     */
    public void orderPancake() {
        System.out.println("面团采用的是【" + dough + "】");
        System.out.println("蔬菜采用的是【" + veggies + "】");
        System.out.println("油条采用的是【" + fritters + "】");
        System.out.println("腐乳采用的是【" + fermentedBeanCurd + "】");
        System.out.println("葱花采用的是【" + choppedGreenOnion + "】");
    }
}
