package com.szlaozicl.designpattern.templatemethod.raw;

/**
 * Coffee和Tea 制作测试
 *
 * @author JustryDeng
 * @date 2020/2/22 17:40:23
 */
public class Test {

    public static void main(String[] args) {
        Coffee coffee = new Coffee();
        coffee.prepareRecipe();

        System.out.println("----");

        Tea tea = new Tea();
        tea.prepareRecipe();
    }
}
