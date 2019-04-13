package com.demo;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 缓存是以key-value进行缓存的，
 *    其中key是按照一定规则生成或我们手动指定的，
 *    value则是 方法的返回值，我们无法进行修改
 *
 * 那么当方法五返回值时，会怎么样呢？
 *
 * 注: 结论是: 对返回值为void的方法进行缓存，放入缓存的value值为null
 *
 * @author JustryDeng
 * @date 2019/4/13 10:58
 */
@Component
public class VoidDemo {

    /**
     * 我们先利用@CachePut将缓存放入进去
     * 注:@Cacheable当然也能放进去，不过@CachePut语意更加明显一点
     */
    @CachePut(cacheNames = "TestVoidSpace", key = "'void-key'")
    public void methodOne() {
    }

    /**
     *
     *
     * 尝试获取{@link com.demo.VoidDemo methodOne}放入的缓存
     */
    @Cacheable(cacheNames = "TestVoidSpace", key = "'void-key'")
    public Object methodTwo() {
        System.out.println("-------------进方法了，说明TestVoidSpace空间下五key为void-key的缓存!");
        return "";
    }
}