package com.szlaozicl.designpattern.command.model;

/**
 * 电风扇
 *
 * @author JustryDeng
 * @date 2020/1/22 11:20
 */
public class Fan {

    public static final int HIGH = 3;

    public static final int MEDIUM = 2;

    public static final int LOW = 1;

    public static final int OFF = 0;

    private int speed;

    /**
     * 高速
     */
    public void high() {
        speed = HIGH;
        System.out.println("电扇现在正【高速】运行！");
    }

    /**
     * 中速
     */
    public void medium() {
        speed = MEDIUM;
        System.out.println("电扇现在正【中速】运行！");
    }

    /**
     * 低速
     */
    public void low() {
        speed = LOW;
        System.out.println("电扇现在正【低速】运行！");
    }

    /**
     * 关
     */
    public void off() {
        speed = OFF;
        System.out.println("电扇已经停止转动了！");
    }

    /**
     * 获取当前转速
     *
     * @return  当前转速
     */
    public int getSpeed() {
        return speed;
    }
}
