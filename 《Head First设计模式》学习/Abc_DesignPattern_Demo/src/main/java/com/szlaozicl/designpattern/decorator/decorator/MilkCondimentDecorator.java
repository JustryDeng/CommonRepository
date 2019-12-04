package com.szlaozicl.designpattern.decorator.decorator;

import com.szlaozicl.designpattern.decorator.AbstractBeverage;

/**
 * 具体的调料(装饰者) - 牛奶Milk
 *
 * @author JustryDeng
 * @date 2019/12/3 12:33
 */
public class MilkCondimentDecorator extends AbstractCondimentDecorator {

    /** 构造 */
    public MilkCondimentDecorator(AbstractBeverage decoratedObj) {
        super(decoratedObj);
    }

    @Override
    public String getDescription() {
        // 被装饰者 原来的description
        String oldDescription = decoratedObj.getDescription();
        // 对原来的description进行装饰
        return oldDescription + " + 牛奶Milk";
    }

    @Override
    public double cost() {
        // 被装饰者 原来的 价格
        double oldCost = decoratedObj.cost();
        // 对原来的价格进行装饰
        return oldCost + 0.1;
    }
}
