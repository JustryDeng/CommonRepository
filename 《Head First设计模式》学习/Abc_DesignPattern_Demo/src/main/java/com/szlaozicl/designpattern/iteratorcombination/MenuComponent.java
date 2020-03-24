package com.szlaozicl.designpattern.iteratorcombination;

import java.util.Iterator;

/**
 * 菜单、菜单项 抽象组件
 *
 * @author JustryDeng
 * @date 2020/3/23 14:00:57
 */
public interface MenuComponent {

    /**
     * 获取名字
     *
     * @return  名字
     */
    default String getName() {
        throw new UnsupportedOperationException("getName");
    }

    /**
     * 获取描述
     *
     * @return  描述
     */
    default String getDescription() {
        throw new UnsupportedOperationException("getDescription");
    }

    /**
     * 获取价格
     *
     * @return  价格
     */
    default double getPrice(){
        throw new UnsupportedOperationException("getPrice");
    }

    /**
     * 是否是蔬菜
     *
     * @return  是否是蔬菜
     */
    default boolean isVegetarian(){
        throw new UnsupportedOperationException("isVegetarian");
    }

    /**
     * 添加项
     *
     * @param menuComponent
     *            组件
     */
    default void add(MenuComponent menuComponent){
        throw new UnsupportedOperationException("add");
    }

    /**
     * 删除项
     *
     * @param menuComponent
     *            组件
     */
    default void remove(MenuComponent menuComponent){
        throw new UnsupportedOperationException("remove");
    }

    /**
     * 获取索引为index的项
     *
     * @param index
     *            索引
     *
     * @return  索引为index的项
     */
    default MenuComponent getChild(int index){
        throw new UnsupportedOperationException("getChild");
    }

    /**
     * 打印信息
     */
    default void print(){
        throw new UnsupportedOperationException("print");
    }

    /**
     * 创建迭代器
     *
     * @return 迭代器
     */
    default Iterator<MenuComponent> createIterator(){
        throw new UnsupportedOperationException("createIterator");
    }

}
