package com.niantou.testcontainer.kafka;

import com.niantou.testcontainer.ExcludedAllAutoConfiguration;
import com.niantou.testcontainer.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * 一个简单的kafka环境支持
 *
 * @author {@link JustryDeng}
 * @since 2020/11/18 12:11:54
 */
@Slf4j
@EnableKafka
@ContextConfiguration(initializers = KafkaSimpleEnvSupport.Initializer.class)
@Import(value = {ExcludedAllAutoConfiguration.class, KafkaAutoConfiguration.class})
public class KafkaSimpleEnvSupport implements KafkaEnvSupport {
    
    /** 
     * 标准的docker镜像(即${镜像名}:${tag名})
     * <p>
     * 提示: image&tag可去https://hub.docker.com/u/library搜索
     */
    private static final String DOCKER_IMAGE_NAME = "confluentinc/cp-kafka:5.4.3";
    
    @ClassRule
    public static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse(DOCKER_IMAGE_NAME));
    
    /**
     * init application context
     *
     * @author {@link JustryDeng}
     * @since 2020/11/14 19:22:23
     */
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        
        @Override
        public void initialize(@SuppressWarnings("NullableProblems") ConfigurableApplicationContext configurableApplicationContext) {
            // start
            kafkaContainer.start();
            
            String bootstrapServers = kafkaContainer.getBootstrapServers();
            System.setProperty("spring.kafka.bootstrap-servers", bootstrapServers);
            // 因为是测试环境， 这里用earliest， 以避免在没有提交offset情况下，用latest不会读取旧数据的问题
            System.setProperty("spring.kafka.consumer.auto-offset-reset", "earliest");
        }
    }
}
