package com.szlaozicl.designpattern.templatemethod.raw;

/**
 * (未使用模板模式的)咖啡类
 *
 * @author JustryDeng
 * @date 2020/2/22 16:49:09
 */
public class Coffee {

    /**
     * 准备咖啡
     */
    public void prepareRecipe() {
        // 第一步: 把水煮沸
        boilWater();
        // 第二步: 用煮沸冲泡咖啡
        brewCoffeeGrinds();
        // 第三步: 把咖啡倒进杯子
        pourInCup();
        // 第四步: 加糖和牛奶
        addSugarAndMilk();
    }

    private void boilWater() {
        System.out.println("把水煮沸");
    }

    private void brewCoffeeGrinds() {
        System.out.println("用煮沸冲泡咖啡");
    }

    private void pourInCup() {
        System.out.println("把咖啡倒进杯子");
    }

    private void addSugarAndMilk() {
        System.out.println("加糖和牛奶");
    }
}
