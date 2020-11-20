package com.niantou.testcontainer.kafka.test;

import com.niantou.testcontainer.author.JustryDeng;
import com.niantou.testcontainer.kafka.KafkaSimpleEnvSupport;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 测试
 *
 * @author {@link JustryDeng}
 * @since 2020/11/18 12:15:47
 */
@Slf4j
@SpringBootTest
@Import(value = {KafkaSimpleTest.MyConsumer.class, KafkaSimpleTest.MyProducer.class})
public class KafkaSimpleTest extends KafkaSimpleEnvSupport {
    
    static final String TOPIC_NAME4TEST = "my-topic4test";
    
    @Resource
    MyProducer myProducer;
    
    @Test
    void one() throws InterruptedException {
        // 生产消息
        for (int i = 0; i < 10; i++) {
            myProducer.sendMessage("邓沙利文-" + i);
        }
        
        // sleep几秒，以便观察消费者是否消费消息
        TimeUnit.SECONDS.sleep(3);
        Assert.assertTrue("消费消息失败(或消费消息超时)", MyConsumer.hasConsumedMsg);
    }
    
    /**
     * 生产者
     */
    @Slf4j
    public static class MyProducer {
        
        @Resource
        private KafkaTemplate<String, String> kafkaTemplate;
        
        public void sendMessage(String message) {
            kafkaTemplate.send(TOPIC_NAME4TEST, "key-" + UUID.randomUUID().toString(), message);
        }
    }
    
    /**
     * 消费者
     */
    @SuppressWarnings("unused")
    @Slf4j
    public static class MyConsumer {
        
        /** 是否已经消费了消息 */
        static boolean hasConsumedMsg = false;
        
        @KafkaListener(topics = TOPIC_NAME4TEST, groupId = "group-b")
        public void consumerOne(String message) {
            log.info("consumerOne消费了消息 [{}]", message);
            hasConsumedMsg = true;
        }
    }
}