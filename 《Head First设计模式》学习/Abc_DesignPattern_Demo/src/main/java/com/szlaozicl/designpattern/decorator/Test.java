package com.szlaozicl.designpattern.decorator;

import com.szlaozicl.designpattern.decorator.beverage.DarkRoast;
import com.szlaozicl.designpattern.decorator.beverage.Decat;
import com.szlaozicl.designpattern.decorator.beverage.Espresso;
import com.szlaozicl.designpattern.decorator.decorator.MilkCondimentDecorator;
import com.szlaozicl.designpattern.decorator.decorator.MochaCondimentDecorator;
import com.szlaozicl.designpattern.decorator.decorator.SoyCondimentDecorator;
import com.szlaozicl.designpattern.decorator.decorator.WhipCondimentDecorator;

/**
 * 装饰者模式 --- 测试
 *
 * @author JustryDeng
 * @date 2019/12/3 12:54
 */
public class Test {

    /** 函数入口 */
    public static void main(String[] args) {
        System.out.println(" ------------- 测试一");
        testOne();

        System.out.println("\n ------------- 测试二");
        testTwo();

        System.out.println("\n ------------- 测试三");
        testThree();
    }

    /** 测试一 */
    private static void testOne() {
        AbstractBeverage beverage = new Espresso();
        System.err.println("装饰前:");
        System.out.println("描述:" + beverage.getDescription());
        System.out.println("价格:" + beverage.cost());

        // 给Espresso装饰 牛奶Milk
        beverage = new MilkCondimentDecorator(beverage);
        System.err.println("装饰后:");
        System.out.println("描述:" + beverage.getDescription());
        System.out.println("价格:" + beverage.cost());
    }

    /** 测试二 */
    private static void testTwo() {
        AbstractBeverage beverage = new DarkRoast();
        System.err.println("装饰前:");
        System.out.println("描述:" + beverage.getDescription());
        System.out.println("价格:" + beverage.cost());

        // 给DarkRoast装饰 牛奶Milk
        beverage = new MilkCondimentDecorator(beverage);
        /*
         * 继续装饰 豆浆Soy
         *
         * 注:虽然直接装饰的对象是MilkCondimentDecorator，但是由
         *    于MilkCondimentDecorator其实是对DarkRoast的装饰，
         *    所以实际上装饰的还是DarkRoast
         */
        beverage = new SoyCondimentDecorator(beverage);
        System.err.println("装饰后:");
        System.out.println("描述:" + beverage.getDescription());
        System.out.println("价格:" + beverage.cost());
    }

    /** 测试三 */
    private static void testThree() {
        AbstractBeverage beverage = new Decat();
        System.err.println("装饰前:");
        System.out.println("描述:" + beverage.getDescription());
        System.out.println("价格:" + beverage.cost());

        // 给DarkRoast装饰 奶泡Whip
        beverage = new WhipCondimentDecorator(beverage);
        // 继续装饰 摩卡Mocha
        beverage = new MochaCondimentDecorator(beverage);
        // 继续装饰 奶泡Whip
        beverage = new WhipCondimentDecorator(beverage);
        System.err.println("装饰后:");
        System.out.println("描述:" + beverage.getDescription());
        System.out.println("价格:" + beverage.cost());
    }
}
