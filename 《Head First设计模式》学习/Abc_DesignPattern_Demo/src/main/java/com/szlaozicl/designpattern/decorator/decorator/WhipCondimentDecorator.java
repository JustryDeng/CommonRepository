package com.szlaozicl.designpattern.decorator.decorator;

import com.szlaozicl.designpattern.decorator.AbstractBeverage;

/**
 * 具体的调料(装饰者) - 奶泡Whip
 *
 * 说明一下:并不是说奶泡的英文是whip,而是指 这个奶泡就名为whip。
 *
 * @author JustryDeng
 * @date 2019/12/3 12:33
 */
public class WhipCondimentDecorator extends AbstractCondimentDecorator {

    /** 构造 */
    public WhipCondimentDecorator(AbstractBeverage decoratedObj) {
        super(decoratedObj);
    }

    @Override
    public String getDescription() {
        // 被装饰者 原来的description
        String oldDescription = decoratedObj.getDescription();
        // 对原来的description进行装饰
        return oldDescription + " + 奶泡Whip";
    }

    @Override
    public double cost() {
        // 被装饰者 原来的 价格
        double oldCost = decoratedObj.cost();
        // 对原来的价格进行装饰
        return oldCost + 0.05;
    }
}
