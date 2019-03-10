package com.single;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 *
 * @author JustryDeng
 * @date 2019/2/15 9:44
 */
@Component
public class MyConsumer {

    /**
     * 注:@KafkaListener注解的属性很多，包括 匹配topic、错误处理、分组等等，
     *    实际使用时根据项目情况灵活运用即可。
     *
     * @author JustryDeng
     * @date 2019/2/15 12:30
     */
    @KafkaListener(topics = {"topicOne", "topicB"}, groupId = "group-01")
    public void consumerOne(String message) {
        System.out.println("consumerOne消费了消息 -> " + message);
    }

    /**
     * 注:@KafkaListener注解的属性很多，包括 匹配topic、错误处理、分组等等，
     *    实际使用时根据项目情况灵活运用即可。
     *
     * @author JustryDeng
     * @date 2019/2/15 12:30
     */
    @KafkaListener(topics = {"topicOne", "topicB"}, groupId = "group-01")
    public void consumerTwo(String message) {
        System.out.println("consumerTwo消费了消息 -> " + message);
    }

    /**
     * 注:@KafkaListener注解的属性很多，包括 匹配topic、错误处理、分组等等，
     *    实际使用时根据项目情况灵活运用即可。
     *
     * @author JustryDeng
     * @date 2019/2/15 12:30
     */
    @KafkaListener(topics = {"topicOne", "topicB"}, groupId = "group-02")
    public void consumerThree(String message) {
        System.out.println("consumerThree消费了消息 -> " + message);
    }
}
