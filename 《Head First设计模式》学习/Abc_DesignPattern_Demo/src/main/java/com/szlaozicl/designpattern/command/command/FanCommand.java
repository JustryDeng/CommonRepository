package com.szlaozicl.designpattern.command.command;

import com.szlaozicl.designpattern.command.model.Fan;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 电扇命令对象
 *
 * @author JustryDeng
 * @date 2020/1/22 11:33
 */
public class FanCommand implements Command {

    /** 命令执行者 */
    private Fan fan;

    /** 要执行的方法 */
    private Method targetMethod;

    /**
     * 构造器
     *
     * @param fan
     *            命令执行者
     * @param speed
     *            一个flag,用于控制 执行哪个 方法
     */
    public FanCommand(Fan fan, int speed) {
        // 指定 命名执行者
        this.fan = fan;

        // 指定 命名执行者 要执行的方法
        try {
            switch (speed) {
                case 3:
                    targetMethod = Fan.class.getDeclaredMethod("high");
                    break;

                case 2:
                    targetMethod = Fan.class.getDeclaredMethod("medium");
                    break;

                case 1:
                    targetMethod = Fan.class.getDeclaredMethod("low");
                    break;

                default:
                    targetMethod = Fan.class.getDeclaredMethod("off");
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute() {
        try {
            targetMethod.invoke(fan);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
