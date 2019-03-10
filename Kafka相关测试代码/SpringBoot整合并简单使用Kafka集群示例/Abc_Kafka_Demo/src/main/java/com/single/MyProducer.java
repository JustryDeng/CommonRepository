package com.single;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 生产者
 *
 * @author JustryDeng
 * @date 2019/2/15 9:44
 */
@Component
public class MyProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public MyProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 发送消息
     * 注:kafkaTemplate.send(param...)是一个异步的方法，其发送结果可以通过Future的实现来获得。
     *
     * @author JustryDeng
     * @date 2019/2/15 12:01
     */
    public void sendMessage(String message){
        String topic = "topicDs";
        // 将消息发送到指定的主题下；这里可以是一个不存在的主题，会自动创建(注:自动创建的主题默认的分区数量是1个)
        kafkaTemplate.send(topic, message);
    }
}