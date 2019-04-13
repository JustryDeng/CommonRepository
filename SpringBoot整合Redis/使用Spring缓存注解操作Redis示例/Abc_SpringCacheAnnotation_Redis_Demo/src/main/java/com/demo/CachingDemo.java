package com.demo;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

/**
 * Caching注解使用示例
 *
 * @author JustryDeng
 * @date 2019/4/13 13:45
 */
@Component
public class CachingDemo {

    @Caching(cacheable = {@Cacheable(cacheNames = "cache-name-one", key = "#a0")},
             put = {@CachePut(cacheNames = "cache-name-two", key = "#a0 + 1")},
             evict = { @CacheEvict(cacheNames = "cache-name-three",  key = "#a0")})
    public Integer fa(Integer i) {
        return i;
    }
}