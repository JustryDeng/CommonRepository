package com.szlaozicl.designpattern.command.command;

import com.szlaozicl.designpattern.command.model.AbstractLight;

/**
 * 开灯命令对象
 *
 * @author JustryDeng
 * @date 2020/1/22 11:01
 */
public class LightOnCommand implements Command {

    AbstractLight light;

    public LightOnCommand(AbstractLight light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }
}
