package com.niantou.redispipeline.env;

import com.niantou.redispipeline.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * redis-cluster环境支持
 * <p>
 * P.S. redis集群使用pipeline清醒比较复杂， 这里直接使用自己搭建的一套测试环境来测试(而不使用test-containers生成的redis-cluster环境)。
 *
 * @author {@link JustryDeng}
 * @since 2020/11/13 16:14:18
 */
@Slf4j
@ContextConfiguration(initializers = RedisClusterEnvSupport.Initializer.class)
public class RedisClusterEnvSupport implements RedisEnvSupport {
    
    /**
     * init application context
     *
     * @author {@link JustryDeng}
     * @since 2020/11/14 19:22:23
     */
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            System.setProperty("spring.redis.cluster.nodes", "192.168.43.97:6379,192.168.43.145:6379,192.168.43.52:6379,192.168.43.52:6380,192.168.43.97:6380,192.168.43.145:6380");
            System.setProperty("spring.redis.password", "ds123");
        }
    }
    
}