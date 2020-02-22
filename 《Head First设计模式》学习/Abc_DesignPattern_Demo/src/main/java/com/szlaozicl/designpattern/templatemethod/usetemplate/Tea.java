package com.szlaozicl.designpattern.templatemethod.usetemplate;

import com.szlaozicl.designpattern.templatemethod.Beverage;

/**
 * (使用了模板模式的)茶叶类
 *
 * 注: 继承模板方法所在的类， 并实现相关方法
 *
 * @author JustryDeng
 * @date 2020/2/22 16:49:09
 */
public class Tea extends Beverage {

    @Override
    protected void brew() {
        System.out.println("用煮沸浸泡茶叶");
    }

    @Override
    protected void addCondiments() {
        System.out.println("加柠檬");
    }
}
