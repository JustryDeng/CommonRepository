package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 定制化RedisTemplate模板
 *
 * @author JustryDeng
 * @date 2019/4/11 16:26
 */
@Configuration
public class MyRedisTemplate {

    @Bean
    @SuppressWarnings("unchecked")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 这里key采用String
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 设置key的 序列化 器
        // 注:RedisTemplate对应的反序列化器与序列化器 一致，设置了序列化器就相当于设置了反序列化起
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        //设置value的 序列化 器
        //注:RedisTemplate对应的反序列化器与序列化器 一致，设置了序列化器就相当于设置了反序列化起
        Jackson2JsonRedisSerializer valueSerializer
                = new Jackson2JsonRedisSerializer(Object.class);
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
