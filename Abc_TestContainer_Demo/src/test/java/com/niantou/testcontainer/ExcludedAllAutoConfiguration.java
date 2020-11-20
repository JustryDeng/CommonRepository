package com.niantou.testcontainer;

import com.niantou.testcontainer.author.JustryDeng;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

/**
 * 排除所有的auto-configuration
 *
 * @author {@link JustryDeng}
 * @since 2020/11/17 20:59:33
 */
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        RedisAutoConfiguration.class,
        KafkaAutoConfiguration.class
})
public class ExcludedAllAutoConfiguration {
}
