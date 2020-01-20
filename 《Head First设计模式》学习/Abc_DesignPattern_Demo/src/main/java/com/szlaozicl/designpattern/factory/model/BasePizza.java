package com.szlaozicl.designpattern.factory.model;

/**
 * 披萨
 *
 * @author JustryDeng
 * @date 2020/1/19 21:57
 */
public abstract class BasePizza {

    /**
     * 披萨名称
     *
     * @return  披萨名称
     */
    protected abstract String name();

    /**
     * 准备(如:添加佐料等)
     */
    public void prepare() {
        System.out.println(name() + ", 佐料添加完毕！");
    }

    /**
     * 烘烤
     */
    public void bake() {
        System.out.println(name() + ", 烘烤完毕！");
    }

    /**
     * 切片
     */
    public void cut() {
        System.out.println(name() + ", 切片完毕！");
    }

    /**
     * 裝盒
     */
    public void box() {
        System.out.println(name() + ", 裝盒完毕！");
    }
}
