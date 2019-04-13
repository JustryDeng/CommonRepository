package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类
 *
 * \@EnableCaching 允许使用注解进行缓存操作
 *
 * @author JustryDeng
 * @date 2019/4/10 18:27
 */
@SpringBootApplication
@EnableCaching
public class CacheAnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheAnnotationApplication.class, args);
    }

}