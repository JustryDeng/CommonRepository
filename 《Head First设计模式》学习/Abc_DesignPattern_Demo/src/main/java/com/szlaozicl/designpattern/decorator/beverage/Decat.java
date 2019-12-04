package com.szlaozicl.designpattern.decorator.beverage;

import com.szlaozicl.designpattern.decorator.AbstractBeverage;

/**
 * 具体饮料 - 低浓咖啡
 *
 * @author JustryDeng
 * @date 2019/12/3 12:03
 */
public class Decat extends AbstractBeverage {

    /** 无参构造 */
    public Decat() {
        // 当前饮料的描述
        setDescription("低浓咖啡Decat");
    }

    @Override
    public double cost() {
        return 1.05;
    }
}
