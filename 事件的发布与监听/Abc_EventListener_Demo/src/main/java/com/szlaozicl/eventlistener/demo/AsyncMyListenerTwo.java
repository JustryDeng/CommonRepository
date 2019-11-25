package com.szlaozicl.eventlistener.demo;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步监听
 *
 * @author JustryDeng
 * @date 2019/11/19 6:44
 */
@Component
public class AsyncMyListenerTwo {

    /**
     * 编写处理事件的逻辑
     *
     * @param event
     *            当前事件对象
     */
    @Async
    @EventListener
    public void abc(MyEvent event) {
        /// 当前事件对象携带的数据
        /// Object source = event.getSource();
        System.out.println(
                "线程-【" + Thread.currentThread().getName() + "】 => "
                        + "监听器-【AsyncMyListenerTwo】 => "
                        + "监听到的事件-【" + event + "】"
        );
    }

}
