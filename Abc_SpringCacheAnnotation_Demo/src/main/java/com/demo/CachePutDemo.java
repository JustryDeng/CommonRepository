package com.demo;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

/**
 * CachePut注解使用示例
 *
 * @author JustryDeng
 * @date 2019/4/13 13:45
 */
@Component
public class CachePutDemo {

    @CachePut(cacheNames = "cache-name-two", key = "#p0", unless = "#result < 5000")
    public Integer fa(Integer i) {
        return i;
    }
}
