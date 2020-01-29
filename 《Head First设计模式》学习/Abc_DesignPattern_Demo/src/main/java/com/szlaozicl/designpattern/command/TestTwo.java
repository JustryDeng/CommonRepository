package com.szlaozicl.designpattern.command;

import com.szlaozicl.designpattern.command.command.FanCommand;
import com.szlaozicl.designpattern.command.model.Fan;

/**
 * 测试二
 *
 * @author JustryDeng
 * @date 2020/1/22 11:45
 */
public class TestTwo {

    /**
     * 主方法
     */
    public static void main(String[] args) {
        /// -> 遥控器
        RemoteControlDevice remoteControlDevice = new RemoteControlDevice(2);

        /// -> 准备指令对象
        // 准备命令执行者
        Fan fan = new Fan();
        // 准备命令对象
        FanCommand fanCommand3 = new FanCommand(fan, 3);
        FanCommand fanCommand2 = new FanCommand(fan, 2);
        FanCommand fanCommand1 = new FanCommand(fan, 1);
        FanCommand fanCommand0 = new FanCommand(fan, 0);

        /// -> 将 命令对象 与遥控器组合起来
        remoteControlDevice.setCommand(0, fanCommand3, fanCommand2);
        remoteControlDevice.setCommand(1, fanCommand1, fanCommand0);

        /// -> 按下遥控器按钮，进行测试
        // 风扇开到高速
        remoteControlDevice.onButtonWasPushed(0);
        // 风扇开到中速
        remoteControlDevice.offButtonWasPushed(0);
        // 风扇开到低速
        remoteControlDevice.onButtonWasPushed(1);
        // 关闭风扇
        remoteControlDevice.offButtonWasPushed(1);
    }
}
