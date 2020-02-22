package com.szlaozicl.designpattern.templatemethod.raw;

/**
 * (未使用模板模式的)茶叶类
 *
 * @author JustryDeng
 * @date 2020/2/22 16:49:09
 */
public class Tea {

    /**
     * 准备茶
     */
    public void prepareRecipe() {
        // 第一步: 把水煮沸
        boilWater();
        // 第二步: 用煮沸浸泡茶叶
        steepTeaBag();
        // 第三步: 把水煮沸
        pourInCup();
        // 第四步: 加柠檬
        addLemon();
    }

    private void boilWater() {
        System.out.println("把水煮沸");
    }

    private void steepTeaBag() {
        System.out.println("用煮沸浸泡茶叶");
    }

    private void pourInCup() {
        System.out.println("把茶倒进杯子");
    }

    private void addLemon() {
        System.out.println("加柠檬");
    }
}
