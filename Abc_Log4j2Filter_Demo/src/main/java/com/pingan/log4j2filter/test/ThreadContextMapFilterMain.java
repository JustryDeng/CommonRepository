package com.pingan.log4j2filter.test;

import com.pingan.log4j2filter.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;

/**
 * 测试使用 ThreadContextMapFilter
 *
 * @author {@link JustryDeng}
 * @since 2020/11/1 3:55:20
 */
@Slf4j
@SuppressWarnings("all")
public class ThreadContextMapFilterMain {
    
    public static void main(String[] args) {
        test2();
    }
    
    public static void test1() {
        ThreadContext.put("name", "JustryDeng");
        log.info("i am info-1");
        ThreadContext.clearAll();
        
        ThreadContext.put("name", "ds");
        log.info("i am info-2");
        ThreadContext.clearAll();
        
        ThreadContext.put("name", "abc");
        log.info("i am info-3");
    }
    
    public static void test2() {
        ThreadContext.put("name", "JustryDeng");
        ThreadContext.put("age", "18");
        log.info("i am info-1");
        ThreadContext.clearAll();

        ThreadContext.put("name", "JustryDeng");
        ThreadContext.put("age", "26");
        log.info("i am info-2");
    }

}
