package com.niantou.redispipeline;

import com.niantou.redispipeline.author.JustryDeng;
import com.niantou.redispipeline.env.RedisStandaloneEnvSupport;
import com.niantou.redispipeline.util.RedisPipelineUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 测试工具类RedisPipelineUtil对单节点redis的pipeline操作
 *
 * @author {@link JustryDeng}
 * @since 2020/11/25 19:17:52
 */
@Slf4j
@SpringBootTest
public class TestUtilStandalone extends RedisStandaloneEnvSupport {
    
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    
    private static final int mount =  10000;

    /**
     * 测试一: 以默认的RedisTemplate来使用Pipeline
     */
    @Test
    void testOne() {
        StopWatch stopWatch = new StopWatch("普通操作与pipeline操作耗时对比");
        
        // ********************** 普通操作 **********************
        stopWatch.start("普通操作存" + mount + "条数据");
        for (int i = 0; i < mount; i++) {
            redisTemplate.opsForValue().set("key" + i, "key" + i + "'s value");
        }
        stopWatch.stop();

        stopWatch.start("普通操作取" + mount + "条数据");
        for (int i = 0; i < mount; i++) {
            System.out.println("普通操作\t" + redisTemplate.opsForValue().get("key" + i));
        }
        stopWatch.stop();
        
        // ********************** pipeline操作 **********************
        stopWatch.start("pipeline存" + mount + "条数据");
        BiConsumer<RedisCommands, String> biConsumer4Set = (redisCommand, str) -> {
            byte[] redisKey = RedisPipelineUtil.getKeySerializer().serialize(str);
            byte[] redisValue = RedisPipelineUtil.getKeySerializer().serialize(str + "'s value(by pipeline)");
            Objects.requireNonNull(redisKey);
            Objects.requireNonNull(redisValue);
            redisCommand.set(redisKey, redisValue);
        };
        List<String> paramList4Set = new ArrayList<>(16);
        for (int i = 0; i < mount; i++) {
            paramList4Set.add("pipeline-key" + i);
        }
        List<Boolean> resultList4Set = RedisPipelineUtil.pipeline4Standalone(biConsumer4Set, paramList4Set);
        resultList4Set.forEach(value -> System.out.println("pipeline set操作\t" + value));
        stopWatch.stop();
    
        stopWatch.start("pipeline取" + mount + "条数据");
        BiConsumer<RedisCommands, String> biConsumer4Get = (redisCommand, str) -> {
            byte[] redisKey = RedisPipelineUtil.getKeySerializer().serialize(str);
            Objects.requireNonNull(redisKey);
            redisCommand.get(redisKey);
        };
        List<String> paramList4Get = new ArrayList<>(paramList4Set);
        List<String> resultList4Get = RedisPipelineUtil.pipeline4Standalone(biConsumer4Get, paramList4Get);
        resultList4Get.forEach(value ->System.out.println("pipeline get操作\t" + value));
        stopWatch.stop();
    
        System.err.println(stopWatch.prettyPrint());
        System.err.println("[验证pipeline是否存在操作丢失] 实际pipeline存了" + resultList4Set.size() + "条");
        System.err.println("[验证pipeline是否存在操作丢失] 实际pipeline取了" + resultList4Get.size() + "条");
    }
    
    /**
     * 测试二: 以指定的RedisTemplate来使用Pipeline
     */
    @Test
    @SuppressWarnings("unchecked")
    void testTwo() {
        // 使用指定的redisTemplate即: 相应的各个数据(包括序列化器)都使用指定的redisTemplate的了
        RedisSerializer<Object> keySerializer = (RedisSerializer<Object>)redisTemplate.getKeySerializer();
        RedisSerializer<Object> valueSerializer = (RedisSerializer<Object>)redisTemplate.getValueSerializer();
        
        
        StopWatch stopWatch = new StopWatch("普通操作与pipeline操作耗时对比");
        
        // ********************** 普通操作 **********************
        stopWatch.start("普通操作存" + mount + "条数据");
        for (int i = 0; i < mount; i++) {
            redisTemplate.opsForValue().set("key" + i, "key" + i + "'s value");
        }
        stopWatch.stop();
        
        stopWatch.start("普通操作取" + mount + "条数据");
        for (int i = 0; i < mount; i++) {
            System.out.println("普通操作\t" + redisTemplate.opsForValue().get("key" + i));
        }
        stopWatch.stop();
        

        // ********************** pipeline操作 **********************
        stopWatch.start("pipeline存" + mount + "条数据");
        BiConsumer<RedisCommands, String> biConsumer4Set = (redisCommand, str) -> {
            byte[] redisKey = keySerializer.serialize(str);
            byte[] redisValue = valueSerializer.serialize(str + "'s value(by pipeline)");
            Objects.requireNonNull(redisKey);
            Objects.requireNonNull(redisValue);
            redisCommand.set(redisKey, redisValue);
        };
        List<String> paramList4Set = new ArrayList<>(16);
        for (int i = 0; i < mount; i++) {
            paramList4Set.add("pipeline-key" + i);
        }
        List<Boolean> resultList4Set = RedisPipelineUtil.pipeline4Standalone(redisTemplate, biConsumer4Set, paramList4Set);
        resultList4Set.forEach(value -> System.out.println("pipeline set操作\t" + value));
        stopWatch.stop();
        
        stopWatch.start("pipeline取" + mount + "条数据");
        BiConsumer<RedisCommands, String> biConsumer4Get = (redisCommand, str) -> {
            byte[] redisKey = keySerializer.serialize(str);
            Objects.requireNonNull(redisKey);
            redisCommand.get(redisKey);
        };
        List<String> paramList4Get = new ArrayList<>(paramList4Set);
        List<String> resultList4Get = RedisPipelineUtil.pipeline4Standalone(redisTemplate, biConsumer4Get, paramList4Get);
        resultList4Get.forEach(value -> System.out.println("pipeline get操作\t" + value));
        stopWatch.stop();
        
        System.err.println(stopWatch.prettyPrint());
        System.err.println("[验证pipeline是否存在操作丢失] 实际pipeline存了" + resultList4Set.size() + "条");
        System.err.println("[验证pipeline是否存在操作丢失] 实际pipeline取了" + resultList4Get.size() + "条");
    }

}
