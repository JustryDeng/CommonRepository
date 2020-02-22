package com.szlaozicl.designpattern.templatemethod;

/**
 * 饮料类 - 模板方法所在的抽象父类
 *
 * @author JustryDeng
 * @date 2020/2/22 16:46:43
 */
public abstract class BeveragePlus {

    /**
     * 制作BeveragePlus
     *
     * 注:因为是模板方法，那么自然不能允许子类修改，所以要定义为final。
     *
     * 注:模板方法里面指定了 步骤大纲。
     *
     */
    public final void prepareRecipe() {

        // 埋点: 前处理钩子
        preHandleHook();
        
        // 这是正常的逻辑流程
        boilWater();
        brew();
        pourInCup();
        addCondiments();

        // 埋点: 流程控制
        if (shouldDoSomething()) {
            doSomething();
        }
        
        // 埋点: 后处理钩子
        postHandleHook();
    }

    /**
     * 前处理钩子, 留空即可
     * 
     * 注: 若需要，则由子类重写。
     */
    protected void preHandleHook() {
        // nothing
    }

    /**
     * 流程控制 - 触发
     * 
     * 注: 默认为false, 若需要，则由子类重写，返回true即可。
     */
    protected boolean shouldDoSomething() {
        return false;
    }

    /**
     * 一些可选的公共逻辑。 由shouldDoSomething()方法的返回值决定是否需要做这些逻辑。
     * 
     * 注: 若需要做这些逻辑，则由子类重写shouldDoSomething(), 返回true即可。
     * 注: 若对这些逻辑不满意，则子类在重写shouldDoSomething()的时候，可同时重写此方法。
     */
    protected void doSomething() {
        // some logic
        System.out.println(" Hi DengShuai~");
        System.out.println(" Hello JustryDeng~");
    }
    
    /**
     * 后处理钩子, 留空即可
     * 
     * 注: 若需要，则由子类重写。
     */
    protected void postHandleHook() {
        // nothing
    }
    
    protected void boilWater() {
        System.out.println("把水煮沸");
    }

    protected void pourInCup() {
        System.out.println("把饮料倒进杯子");
    }

    /**
     * 冲泡
     */
    protected abstract void brew();

    /**
     * 添加附加品
     */
    protected abstract void addCondiments();
}
