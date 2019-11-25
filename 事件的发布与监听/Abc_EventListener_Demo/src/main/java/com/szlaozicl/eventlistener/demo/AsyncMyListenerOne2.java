package com.szlaozicl.eventlistener.demo;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步监听
 *
 * @author JustryDeng
 * @date 2019/11/19 6:44
 */
@Component
public class AsyncMyListenerOne2 implements ApplicationListener<MyEvent> {

    /**
     * 编写处理事件的逻辑
     *
     * @param event
     *            当前事件对象
     */
    @Override
    @Async(value = "myAsyncExecutor")
    public void onApplicationEvent(MyEvent event) {
        /// 当前事件对象携带的数据
        /// Object source = event.getSource();
        System.out.println(
                "线程-【" + Thread.currentThread().getName() + "】 => "
                + "监听器-【AsyncMyListenerOne2】 => "
                + "监听到的事件-【" + event + "】"
        );
    }
}
