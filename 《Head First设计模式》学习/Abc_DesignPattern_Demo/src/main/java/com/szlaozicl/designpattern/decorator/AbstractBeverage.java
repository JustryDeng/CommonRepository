package com.szlaozicl.designpattern.decorator;

import lombok.Getter;
import lombok.Setter;

/**
 * 饮料(的抽象定义)
 *
 * 注: 在java中的装饰者模式，“最顶级的类”既可采用【抽象类】，亦可采用【接口】。
 *     不过一般都采用 抽象类。
 *
 * @author JustryDeng
 * @date 2019/12/3 11:45
 */
public abstract class AbstractBeverage {

    /** 描述 */
    @Setter
    @Getter
    private String description;

    /**
     * 获取饮料的价格
     *
     * @return  饮料的价格
     */
    public abstract double cost();
}
