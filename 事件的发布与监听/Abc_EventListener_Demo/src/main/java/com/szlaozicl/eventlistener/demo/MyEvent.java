package com.szlaozicl.eventlistener.demo;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件
 *
 * 注:继承ApplicationEvent即可。
 *
 * @author JustryDeng
 * @date 2019/11/19 6:36
 */
public class MyEvent extends ApplicationEvent {

    /**
     * 构造器
     *
     * @param source
     *            该事件的相关数据
     *
     * @date 2019/11/19 6:40
     */
    public MyEvent(Object source) {
        super(source);
    }
}
