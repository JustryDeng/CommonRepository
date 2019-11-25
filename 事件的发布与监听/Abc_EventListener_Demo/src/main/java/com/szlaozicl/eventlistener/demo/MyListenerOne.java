package com.szlaozicl.eventlistener.demo;

import org.springframework.context.ApplicationListener;

/**
 * 自定义监听器
 *
 * 注:实现ApplicationListener<E extends ApplicationEvent>即可，
 *    其中E为此监听器要监听的事件。
 *
 * @author JustryDeng
 * @date 2019/11/19 6:44
 */
public class MyListenerOne implements ApplicationListener<MyEvent> {

    /**
     * 编写处理事件的逻辑
     *
     * @param event
     *            当前事件对象
     */
    @Override
    public void onApplicationEvent(MyEvent event) {
        /// 当前事件对象携带的数据
        /// Object source = event.getSource();
        System.out.println(
                "线程-【" + Thread.currentThread().getName() + "】 => "
                + "监听器-【MyListenerOne】 => "
                + "监听到的事件-【" + event + "】"
        );

    }
}
