package com.szlaozicl.designpattern.decorator.decorator;

import com.szlaozicl.designpattern.decorator.AbstractBeverage;

/**
 * (装饰饮料的)调料装饰者(的抽象定义)
 *
 * @author JustryDeng
 * @date 2019/12/3 11:50
 */
@SuppressWarnings("all")
public abstract class AbstractCondimentDecorator extends AbstractBeverage {

    /** 被装饰者 */
    protected AbstractBeverage decoratedObj;

    /**
     * 构造器
     *
     * @param decoratedObj
     *            被装饰者
     * @date 2019/12/4 12:30
     */
    protected AbstractCondimentDecorator(AbstractBeverage decoratedObj) {
        this.decoratedObj = decoratedObj;
    }

    /**
     * 装饰者应重写 饮料的描述
     * 注: 之所以需要重写饮料的描述, 是因为原来的描述没有对调料的介绍。
     * 特别注意: 装饰者 应重写 装饰前后相关的方法。 如，这里的描述、价格。
     *
     * @return 饮料的描述
     */
    @Override
    public abstract String getDescription();
}
