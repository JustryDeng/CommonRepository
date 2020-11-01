package com.pingan.log4j2filter.test;

import com.pingan.log4j2filter.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;

/**
 * 测试使用 DynamicThresholdFilter
 *
 * @author {@link JustryDeng}
 * @since 2020/11/1 3:55:20
 */
@Slf4j
public class DynamicThresholdFilterMain {
    
    public static void main(String[] args) {
        ThreadContext.put("userRole", "admin");
        log.debug("[test1] i am debug");
        log.info("[test1] i am info");
        log.warn("[test1] i am warn");
        log.error("[test1] i am error");

        ThreadContext.put("userRole", "dba");
        log.debug("[test2] i am debug");
        log.info("[test2] i am info");
        log.warn("[test2] i am warn");
        log.error("[test2] i am error");

        ThreadContext.put("userRole", "user");
        log.debug("[test3] i am debug");
        log.info("[test3] i am info");
        log.warn("[test3] i am warn");
        log.error("[test3] i am error");
    }
}
