package com.szlaozicl.designpattern.command;

import com.szlaozicl.designpattern.command.command.LightOffCommand;
import com.szlaozicl.designpattern.command.command.LightOnCommand;
import com.szlaozicl.designpattern.command.model.AbstractLight;
import com.szlaozicl.designpattern.command.model.KitchenLight;
import com.szlaozicl.designpattern.command.model.LivingRoomLight;

/**
 * 测试一
 *
 * @author JustryDeng
 * @date 2020/1/22 11:45
 */
public class TestOne {

    /**
     * 主方法
     */
    public static void main(String[] args) {
        /// -> 遥控器
        RemoteControlDevice remoteControlDevice = new RemoteControlDevice(2);

        /// -> 准备指令对象
        // 准备命令执行者
        AbstractLight kitchenLight = new KitchenLight();
        // 准备命令对象
        LightOnCommand kitchenLightOnCommand = new LightOnCommand(kitchenLight);
        LightOffCommand kitchenLightOffCommand = new LightOffCommand(kitchenLight);

        // 准备命令执行者
        AbstractLight livingRoomLight = new LivingRoomLight();
        // 准备命令对象
        LightOnCommand livingRoomLightOnCommand = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOffCommand = new LightOffCommand(livingRoomLight);

        /// -> 将 命令对象 与遥控器组合起来
        remoteControlDevice.setCommand(0, kitchenLightOnCommand, kitchenLightOffCommand);
        remoteControlDevice.setCommand(1, livingRoomLightOnCommand, livingRoomLightOffCommand);

        /// -> 按下遥控器按钮，进行测试
        // 打开厨房灯
        remoteControlDevice.onButtonWasPushed(0);
        // 关闭厨房灯
        remoteControlDevice.offButtonWasPushed(0);
        System.out.println();
        // 打开客厅灯
        remoteControlDevice.onButtonWasPushed(1);
        // 关闭客厅灯
        remoteControlDevice.offButtonWasPushed(1);
    }
}
