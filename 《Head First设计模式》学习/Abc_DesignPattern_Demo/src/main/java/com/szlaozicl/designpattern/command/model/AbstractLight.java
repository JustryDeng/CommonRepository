package com.szlaozicl.designpattern.command.model;

/**
 * 灯
 *
 * @author JustryDeng
 * @date 2020/1/22 10:42
 */
public abstract class AbstractLight {

    public static final String ON = "on";

    public static final String OFF = "off";

    /**
     * 灯的类型
     * 如: 客厅灯、卧室灯、厨房灯、厕所灯 等等
     */
    protected String type;

    /** 灯的状态(off - 关， on - 开) */
    private String status;

    /**
     * 指定灯的类型
     *
     * @date 2020/1/22 10:45
     */
    protected abstract void initLightType();

    /**
     * 开灯
     *
     * @date 2020/1/22 10:52
     */
    public void on() {
        this.status = ON;
        System.out.println(type + " 开了！");
    }

    /**
     * 关灯
     *
     * @date 2020/1/22 10:52
     */
    public void off() {
        this.status = OFF;
        System.out.println(type + " 关了！");
    }

    /**
     * 获取当前灯的状态
     */
    @SuppressWarnings("unused")
    public String getStatus() {
        return status;
    }
}
