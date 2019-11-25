package com.szlaozicl.eventlistener.demo;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 自定义监听器
 *
 * 注:在某个方法上使用@EventListener注解即可。
 *    追注一: 这个方法必须满足: 最多能有一个参数。
 *    追注二:    若只是监听一种事件，那么这个方法的参数类型应为该事
 *           件对象类；P.S.:该事件的子类事件，也属于该事件，也会被监听到。
 *              若要监听多种事件，那么可以通过@EventListener注解
 *           的classes属性指定多个事件，且保证这个方法无参；
 *
 *
 *
 * @author JustryDeng
 * @date 2019/11/19 6:44
 */
@Component
public class MyListenerTwo {

    /**
     * 编写处理事件的逻辑
     *
     * @param event
     *            当前事件对象
     */
    @EventListener
    public void abc(MyEvent event) {
        /// 当前事件对象携带的数据
        /// Object source = event.getSource();
        System.out.println(
                "线程-【" + Thread.currentThread().getName() + "】 => "
                        + "监听器-【MyListenerTwo】 => "
                        + "监听到的事件-【" + event + "】"
        );
    }

}
