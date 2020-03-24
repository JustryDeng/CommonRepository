package com.szlaozicl.designpattern.iteratorcombination.impl;

import com.szlaozicl.designpattern.iteratorcombination.MenuComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 菜单
 *
 * 注: 菜单，可以有:
 *     - 菜单名
 *     - 菜单描述
 *     - (针对子菜单组件的功能:)添加菜单组件(添加 子菜单or菜单项)
 *     - (针对子菜单组件的功能:)移除菜单组件(移除 子菜单or菜单项)
 *     - (针对子菜单组件的功能:)获取菜单组件
 *     - 打印
 *
 * @author JustryDeng
 * @date 2020/3/23 14:38:26
 */
public class Menu implements MenuComponent {

    /** 子菜单组件 */
    List<MenuComponent> subMenuComponent;

    /** 菜名 */
    private String name;

    /** 菜描述 */
    private String description;

    public Menu(String name, String description) {
        this(new ArrayList<>(8), name, description);
    }

    public Menu(List<MenuComponent> subMenuComponent, String name, String description) {
        this.subMenuComponent = subMenuComponent;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void add(MenuComponent menuComponent) {
        subMenuComponent.add(menuComponent);
    }

    @Override
    public void remove(MenuComponent menuComponent) {
        subMenuComponent.remove(menuComponent);
    }

    @Override
    public MenuComponent getChild(int index) {
        return subMenuComponent.get(index);
    }

    /**
     * 打印菜品信息
     */
    @Override
    public void print() {
        Objects.requireNonNull(subMenuComponent, "subMenuComponent cannot be null");
        for (MenuComponent menuComponent : subMenuComponent) {
            menuComponent.print();
        }
    }

    @Override
    public Iterator<MenuComponent> createIterator() {
        return subMenuComponent.iterator();
    }
}
