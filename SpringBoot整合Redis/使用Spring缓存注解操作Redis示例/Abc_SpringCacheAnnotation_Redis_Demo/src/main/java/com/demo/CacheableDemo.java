package com.demo;

import com.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Cacheable注解使用示例
 *
 * @author JustryDeng
 * @date 2019/4/13 13:45
 */
@Component
public class CacheableDemo {

    @Cacheable(cacheNames = "cache-name-one", key = "#str.hashCode() + '*****' + #user.name")
    public String fa(String str, User user) {
        return "咿呀咔咔！";
    }
}
