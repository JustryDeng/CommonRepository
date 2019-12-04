package com.szlaozicl.designpattern.decorator.beverage;

import com.szlaozicl.designpattern.decorator.AbstractBeverage;

/**
 * 具体饮料 - 焦炒咖啡
 *
 * @author JustryDeng
 * @date 2019/12/3 12:03
 */
public class DarkRoast extends AbstractBeverage {

    /** 无参构造 */
    public DarkRoast() {
        // 当前饮料的描述
        setDescription("焦炒咖啡DarkRoast");
    }

    @Override
    public double cost() {
        return 0.99;
    }
}
