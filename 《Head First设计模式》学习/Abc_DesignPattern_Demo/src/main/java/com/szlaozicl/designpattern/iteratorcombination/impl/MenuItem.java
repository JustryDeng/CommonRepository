package com.szlaozicl.designpattern.iteratorcombination.impl;

import com.szlaozicl.designpattern.iteratorcombination.MenuComponent;
import java.util.Iterator;

/**
 * 具体的菜(单项)
 *
 * 注: 菜单项，可以有:
 *     - 菜名
 *     - 菜描述
 *     - 菜价格
 *     - 是否是蔬菜
 *     - 打印
 *
 * @author JustryDeng
 * @date 2020/3/23 14:36:41
 */
public class MenuItem implements MenuComponent {

    /** 菜名 */
    private String name;

    /** 菜描述 */
    private String description;

    /** 菜价格 */
    private double price;

    /** 是否是蔬菜 */
    private boolean vegetarian;

    public MenuItem(String name, String description, double price, boolean vegetarian) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.vegetarian = vegetarian;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public boolean isVegetarian() {
        return this.vegetarian;
    }

    /**
     * 打印菜品信息
     */
    @Override
    public void print() {
        String content = " => 【" + name + "】" + (vegetarian ? "是" : "不是") + "蔬菜: "
                + "\n\t\t其它介绍：" + description
                + "\n\t\t价格：" + price + "RMB";
        System.out.println(content);
    }

    @Override
    public Iterator<MenuComponent> createIterator() {
        return new NullIterator<>();
    }

    /**
     * 一个空的迭代对象
     *
     * @author JustryDeng
     * @date 2020/3/23 16:48:12
     */
    public static class NullIterator<T> implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }
    }
}
