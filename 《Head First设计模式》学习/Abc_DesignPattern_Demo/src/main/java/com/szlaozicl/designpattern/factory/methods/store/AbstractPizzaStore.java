package com.szlaozicl.designpattern.factory.methods.store;

import com.szlaozicl.designpattern.factory.model.BasePizza;

/**
 * 工厂方法 抽象父类
 *
 * @author JustryDeng
 * @date 2020/1/19 22:11
 */
public abstract class AbstractPizzaStore {

    /**
     * 获取生披萨
     *
     * @param type
     *         披萨类型
     * @return 生披萨
     */
    protected abstract BasePizza createPizza(String type);

    /**
     * 定制披萨
     *
     * @param type
     *         披萨类型
     */
    public void orderPizza(String type) {
        //  第一步，利用createPizza(String type)方法，获取到生的披萨
        BasePizza pizza = createPizza(type);

        // 第二步，将生披萨加工成熟披萨
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }
}
