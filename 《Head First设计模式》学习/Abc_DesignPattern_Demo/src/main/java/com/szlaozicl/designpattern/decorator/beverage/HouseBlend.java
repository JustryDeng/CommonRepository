package com.szlaozicl.designpattern.decorator.beverage;

import com.szlaozicl.designpattern.decorator.AbstractBeverage;

/**
 * 具体饮料 - 首选咖啡
 *
 * @author JustryDeng
 * @date 2019/12/3 12:03
 */
public class HouseBlend extends AbstractBeverage {

    /** 无参构造 */
    public HouseBlend() {
        // 当前饮料的描述
        setDescription("首选咖啡HouseBlend");
    }

    @Override
    public double cost() {
        return 0.89;
    }
}
