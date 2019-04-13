package com.demo;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * CacheConfig注解使用示例
 *
 * @author JustryDeng
 * @date 2019/4/13 13:45
 */
@Component
@CacheConfig(cacheNames = {"cache-name-nb"})
public class CacheConfigDemo {

    @Cacheable(key = "#str")
    public String faOne(String str) {
        return "咿呀咔咔！";
    }

    @CacheEvict( key = "#str")
    public String faTwo(String str) {
        return "我是一只小小小小小小鸟~";
    }

    @CachePut(key = "#str")
    public String faThree(String str) {
        return "这里的山路十八弯~";
    }
}