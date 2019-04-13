package com.demo;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

/**
 * CacheEvict注解使用示例
 *
 * @author JustryDeng
 * @date 2019/4/13 13:45
 */
@Component
public class CacheEvictDemo {

    @CacheEvict(cacheNames = "cache-name-three", key = "#p0", beforeInvocation = true)
    public String fa(String str) {
        return "我是一只小小小小小小鸟~";
    }

}