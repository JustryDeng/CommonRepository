package com.szlaozicl.designpattern.decorator.beverage;

import com.szlaozicl.designpattern.decorator.AbstractBeverage;

/**
 * 具体饮料 - 浓缩咖啡
 *
 * @author JustryDeng
 * @date 2019/12/3 12:03
 */
public class Espresso extends AbstractBeverage {

    /** 无参构造 */
    public Espresso() {
        // 当前饮料的描述
        setDescription("浓缩咖啡Espresso");
    }

    @Override
    public double cost() {
        return 1.99;
    }
}
