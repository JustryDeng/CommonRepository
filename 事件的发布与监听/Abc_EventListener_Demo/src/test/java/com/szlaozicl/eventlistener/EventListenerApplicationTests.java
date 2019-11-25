package com.szlaozicl.eventlistener;

import com.szlaozicl.eventlistener.demo.MyEvent;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class EventListenerApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 简单测试 事件监听
     *
     * @date 2019/11/25 10:13
     */
    @Test
    void contextLoads() {
        // 事件
        MyEvent myEvent = new MyEvent("我是一只小小小小鸟~嗷！嗷！");
        // 发布事件
        applicationContext.publishEvent(myEvent);
        for (int i = 0; i < 100; i++) {
            System.out.println("同步监听：" + i);
        }
    }

    /**
     * 多线程测试 事件监听
     *
     * @date 2019/11/25 10:13
     */
    @Test
    void test() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            // 主动开线程，在其他线程发布事件
            EventRunnableImpl eri = new EventRunnableImpl();
            eri.initParams(applicationContext);
            Thread thread = new Thread(eri, "线程" + i);
            thread.start();
        }
        TimeUnit.SECONDS.sleep(5);
    }

}

@Data
class EventRunnableImpl implements Runnable {

    private ApplicationContext applicationContext;

    void initParams(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        if (applicationContext == null) {
            throw new RuntimeException("please invoke method【initParams】, before invoke method【run】");
        }
        MyEvent myEvent;
        for (int i = 0; i < 10; i++) {
            myEvent = new MyEvent("蚂蚁呀~嘿~" + i);
            applicationContext.publishEvent(myEvent);
        }
    }
}
