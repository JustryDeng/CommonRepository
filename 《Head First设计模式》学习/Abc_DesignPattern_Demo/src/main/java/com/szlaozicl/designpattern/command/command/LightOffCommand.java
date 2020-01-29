package com.szlaozicl.designpattern.command.command;

import com.szlaozicl.designpattern.command.model.AbstractLight;

/**
 * 关灯命令对象
 *
 * @author JustryDeng
 * @date 2020/1/22 11:01
 */
public class LightOffCommand implements Command {

    AbstractLight light;

    public LightOffCommand(AbstractLight light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }
}
