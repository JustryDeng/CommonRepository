package com.szlaozicl.eventlistener;

import com.szlaozicl.eventlistener.demo.MyEvent;
import com.szlaozicl.eventlistener.demo.MyListenerOne;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @author JustryDeng
 * @date 2019/11/10 19:06
 */
@EnableAsync
@SpringBootApplication
public class EventListenerApplication {

    public static void main(String[] args) {
        // 先获取SpringApplication
        SpringApplication springApplication = new SpringApplication(EventListenerApplication.class);
        // 添加(注册)监听器
        springApplication.addListeners(new MyListenerOne());
        // 启动SpringBoot项目
        ApplicationContext applicationContext = springApplication.run(args);

        // 测试一下
        test(applicationContext);
    }

    private static void test(ApplicationContext applicationContext){
        // 事件
        MyEvent myEvent = new MyEvent("我是一只小小小小鸟~嗷！嗷！");
        // 发布事件
        applicationContext.publishEvent(myEvent);
    }
}
