package com.niantou.testcontainer.rabbitmq;

import com.niantou.testcontainer.ExcludedAllAutoConfiguration;
import com.niantou.testcontainer.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.RabbitMQContainer;

/**
 * 一个简单的RabbitMQ环境支持
 *
 * @author {@link JustryDeng}
 * @since 2020/11/20 12:49:45
 */
@Slf4j
@ContextConfiguration(initializers = RabbitMqSimpleEnvSupport.Initializer.class)
@Import(value = {ExcludedAllAutoConfiguration.class})
public class RabbitMqSimpleEnvSupport {
    
    /** 
     * 标准的docker镜像(即${镜像名}:${tag名})
     * <p>
     * 提示: image&tag可去https://hub.docker.com/u/library搜索
     */
    private static final String DOCKER_IMAGE_NAME = "rabbitmq:management-alpine";
    
    @ClassRule
    public static RabbitMQContainer rabbitMqContainer = new RabbitMQContainer(DOCKER_IMAGE_NAME)
            .withAdminPassword("ds123");
            
    
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
            rabbitMqContainer.start();
            
            String host = rabbitMqContainer.getHost();
            log.info("spring.rabbitmq.host={}", host);
            System.setProperty("spring.rabbitmq.host", host);
            
            Integer amqpPort = rabbitMqContainer.getAmqpPort();
            log.info("spring.rabbitmq.port={}", amqpPort);
            System.setProperty("spring.rabbitmq.port", String.valueOf(amqpPort));
    
            String adminUsername = rabbitMqContainer.getAdminUsername();
            log.info("spring.rabbitmq.username={}", adminUsername);
            System.setProperty("spring.rabbitmq.username", adminUsername);
    
            String adminPassword = rabbitMqContainer.getAdminPassword();
            log.info("spring.rabbitmq.password={}", adminPassword);
            System.setProperty("spring.rabbitmq.password", adminPassword);
        }
    }
    
}
