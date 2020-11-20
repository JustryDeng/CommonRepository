package com.niantou.testcontainer.redis.test;

import com.niantou.testcontainer.author.JustryDeng;
import com.niantou.testcontainer.redis.RedisStandaloneEnvSupport;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 测试
 *
 * @author {@link JustryDeng}
 * @since 2020/11/13 16:36:21
 */
@Slf4j
@SpringBootTest
public class RedisStandaloneTest extends RedisStandaloneEnvSupport {
    
    @Resource
    StringRedisTemplate stringRedisTemplate;
    
    @Test
    void one() throws InterruptedException {
        String name = stringRedisTemplate.opsForValue().get("name");
        System.err.println(name);
        Assert.assertNull(name);
        
        String justryDeng = "JustryDeng";
        stringRedisTemplate.opsForValue().set("name", justryDeng, Duration.ofSeconds(3));
        name = stringRedisTemplate.opsForValue().get("name");
        System.err.println(name);
        Assert.assertEquals(justryDeng, name);
        
        TimeUnit.SECONDS.sleep(3);
        name = stringRedisTemplate.opsForValue().get("name");
        System.err.println(name);
        Assert.assertNull(name);
    }

}
