package com.szlaozicl.designpattern.decorator.decorator;

import com.szlaozicl.designpattern.decorator.AbstractBeverage;

/**
 * 具体的调料(装饰者) - 豆浆Soy
 *
 * @author JustryDeng
 * @date 2019/12/3 12:33
 */
public class SoyCondimentDecorator extends AbstractCondimentDecorator {

    /** 构造 */
    public SoyCondimentDecorator(AbstractBeverage decoratedObj) {
        super(decoratedObj);
    }

    @Override
    public String getDescription() {
        // 被装饰者 原来的description
        String oldDescription = decoratedObj.getDescription();
        // 对原来的description进行装饰
        return oldDescription + " + 豆浆Soy";
    }

    @Override
    public double cost() {
        // 被装饰者 原来的 价格
        double oldCost = decoratedObj.cost();
        // 对原来的价格进行装饰
        return oldCost + 0.15;
    }
}
