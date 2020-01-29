package com.szlaozicl.designpattern.command;

import com.szlaozicl.designpattern.command.command.Command;
import com.szlaozicl.designpattern.command.command.NoCommand;

/**
 * 遥控器
 *
 * @author JustryDeng
 * @date 2020/1/22 11:48
 */
public class RemoteControlDevice {

    Command[] onCommands;
    Command[] offCommands;

    /**
     * 构造器
     *
     * @param length
     *         命令对象数组 长度
     */
    public RemoteControlDevice(final int length) {
        this.onCommands = new Command[length];
        this.offCommands = new Command[length];
        // 如果不设置NoCommand的话，那么每次执行Command前，都需要判断有没有Command对象才行。
        // 设置NoCommand之后,就可以不同判断了，直接执行即可。
        NoCommand noCommand = new NoCommand();
        for (int i = 0; i < length; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }

    /**
     * 设置指令对象
     *
     * @param index
     *         位置
     * @param onCommand
     *         开 - 指令对象
     * @param offCommand
     *         关 - 指令对象
     * @date 2020/1/22 11:56
     */
    public void setCommand(int index, Command onCommand, Command offCommand) {
        onCommands[index] = onCommand;
        offCommands[index] = offCommand;
    }

    /**
     * 遥控器按键 开
     *
     * @param index
     *            要开的command的位置索引
     * @date 2020/1/22 12:07
     */
    public void onButtonWasPushed(int index) {
        onCommands[index].execute();
    }

    /**
     * 遥控器按键 关
     *
     * @param index
     *            要关的command的位置索引
     * @date 2020/1/22 12:07
     */
    public void offButtonWasPushed(int index) {
        offCommands[index].execute();
    }

}
