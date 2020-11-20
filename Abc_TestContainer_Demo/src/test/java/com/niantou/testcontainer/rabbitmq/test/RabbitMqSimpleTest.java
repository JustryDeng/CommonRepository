package com.niantou.testcontainer.rabbitmq.test;

import com.niantou.testcontainer.author.JustryDeng;
import com.niantou.testcontainer.rabbitmq.RabbitMqSimpleEnvSupport;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.niantou.testcontainer.rabbitmq.test.RabbitMqSimpleTest.MyConfig.MY_EXCHANGE_NAME;
import static com.niantou.testcontainer.rabbitmq.test.RabbitMqSimpleTest.MyConfig.QUEUE_NAME;
import static com.niantou.testcontainer.rabbitmq.test.RabbitMqSimpleTest.MyConfig.ROUTING_KEY;

/**
 * 测试
 *
 * @author {@link JustryDeng}
 * @since 2020/11/18 12:15:47
 */
@Slf4j
@SpringBootTest
@Import(value = {RabbitMqSimpleTest.MyConsumer.class, RabbitMqSimpleTest.MyProducer.class,
        RabbitMqSimpleTest.MyConfig.class})
public class RabbitMqSimpleTest extends RabbitMqSimpleEnvSupport {
    
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
     * 配置类
     */
    @Slf4j
    public static class MyConfig {
        
        static final String ROUTING_KEY = "myRoutingKey";
        
        static final String QUEUE_NAME = "my-queue";
        
        static final String MY_EXCHANGE_NAME = "my-direct-exchange";
        
        @Bean
        public Queue myQueue() {
            return new Queue(QUEUE_NAME);
        }
        
        @Bean
        public DirectExchange myDirectExchange() {
            return new DirectExchange(MY_EXCHANGE_NAME);
        }
        
        @Bean
        public Binding myBinding(Queue myQueue, DirectExchange myDirectExchange) {
            return BindingBuilder.bind(myQueue).to(myDirectExchange).with(ROUTING_KEY);
        }
        
    }
    
    /**
     * 生产者
     */
    @Slf4j
    public static class MyProducer {
        
        @Resource
        private AmqpTemplate amqpTemplate;
        
        public void sendMessage(String message) {
            amqpTemplate.convertAndSend(MY_EXCHANGE_NAME, ROUTING_KEY, message);
        }
    }
    
    /**
     * 消费者
     */
    @Slf4j
    public static class MyConsumer {
        
        /** 是否已经消费了消息 */
        static boolean hasConsumedMsg = false;
        
        @SuppressWarnings("unused")
        @RabbitListener(queues = QUEUE_NAME)
        public void consumerOne(String message) {
            log.info("consumerOne消费了消息 [{}]", message);
            hasConsumedMsg = true;
        }
    }
}