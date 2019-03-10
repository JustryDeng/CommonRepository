package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogbackFlumeApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogbackFlumeApplicationTests.class);

    @Test
    public void contextLoads() {
        for (int i = 1; i <= 10; i++) {
            LOGGER.info("我是第 " + i + " 条信息！");
        }
        try {
            // 由于日志往flume写需要一个过程，所以我们不能让程序过早停止
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}