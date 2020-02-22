package com.szlaozicl.designpattern.templatemethod;

/**
 * 饮料类 - 模板方法所在的抽象父类
 *
 * @author JustryDeng
 * @date 2020/2/22 16:46:43
 */
public abstract class Beverage {

    /**
     * 制作Beverage
     *
     * 注:因为是模板方法，那么自然不能允许子类修改，所以要定义为final。
     *
     * 注:模板方法里面指定了步骤大纲。
     *
     */
    public final void prepareRecipe() {
        /*
          * 第一步:把水煮沸。
          * 注: 因为这一步都是一样的，所以直接在本类里面实现。
         */
        boilWater();

        /*
         * 第二步:冲泡。
         * 注: 因为每一种饮料的这一步都可能不同，所以brew定义为抽象方法，由具体子类提供实现。
         */
        brew();

        /*
         * 第三步:把饮料倒进杯子。
         * 注: 因为这一步都是一样的，所以直接在本类里面实现。
         */
        pourInCup();

        /*
         * 第四步:添加附加品。
         * 注: 因为每一种饮料的这一步都可能不同，所以brew定义为抽象方法，由具体子类提供实现。
         */
        addCondiments();
    }

    protected void boilWater() {
        System.out.println("把水煮沸");
    }

    protected void pourInCup() {
        System.out.println("把饮料倒进杯子");
    }

    /**
     * 冲泡
     */
    protected abstract void brew();

    /**
     * 添加附加品
     */
    protected abstract void addCondiments();
}
