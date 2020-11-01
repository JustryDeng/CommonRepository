package com.pingan.logbackfilter.test;

import com.pingan.logbackfilter.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * 测试 DynamicThresholdFilter
 *
 * @author {@link JustryDeng}
 * @since 2020/11/1 13:06:58
 */
@Slf4j
public class DynamicThresholdFilterMain {
    
    public static void main(String[] args) {
        MDC.put("userRole", "admin");
        log.debug("[test1] i am debug");
        log.info("[test1] i am info");
        log.warn("[test1] i am warn");
        log.error("[test1] i am error");
        
        MDC.put("userRole", "dba");
        log.debug("[test2] i am debug");
        log.info("[test2] i am info");
        log.warn("[test2] i am warn");
        log.error("[test2] i am error");
        
        MDC.put("userRole", "user");
        log.debug("[test3] i am debug");
        log.info("[test3] i am info");
        log.warn("[test3] i am warn");
        log.error("[test3] i am error");
    
        MDC.put("userRole", "abc");
        log.debug("[test4] i am debug");
        log.info("[test4] i am info");
        log.warn("[test4] i am warn");
        log.error("[test4] i am error");
    
    
        MDC.put("non", "non");
        log.debug("[test5] i am debug");
        log.info("[test5] i am info");
        log.warn("[test5] i am warn");
        log.error("[test5] i am error");
    }
}
