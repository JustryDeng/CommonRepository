package com.szlaozicl.designpattern.command.command;

/**
 * 一个不做任何逻辑的 指令对象
 *
 * @author JustryDeng
 * @date 2020/1/22 11:52
 */
public class NoCommand implements Command {

    @Override
    public void execute() {
        // do nothing
    }
}
