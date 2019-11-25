package com.szlaozicl.eventlistener.controller;

import com.szlaozicl.eventlistener.demo.MyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller
 *
 * @author JustryDeng
 * @date 2019/11/25 8:57
 */
@RestController
public class DemoController {

    private final ApplicationContext applicationContext;

    public DemoController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 可以使用Jmeter并发测试，以验证:
     *    假设某线程α，线程β都有发布各自的事件，那么α线程发布的事件会被α线程进行监听器逻辑处理，
     *    β线程发布的事件会被β线程进行监听器逻辑处理。
     *
     * 提示: 为方便观察，建议多线程并发测试时只开一个监听器。
     *
     * @date 2019/11/25 10:57
     */
    @GetMapping("/fa")
    public void fa() {
        MyEvent event;
        for (int i = 0; i < 10; i++) {
            System.err.println("当前请求的线程为: " + Thread.currentThread().getName());
            event = new MyEvent("fa - " + i);
            applicationContext.publishEvent(event);
        }
    }
}
