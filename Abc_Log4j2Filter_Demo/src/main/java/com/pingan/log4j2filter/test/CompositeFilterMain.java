package com.pingan.log4j2filter.test;

import com.pingan.log4j2filter.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试使用 CompositeFilter
 *
 * @author {@link JustryDeng}
 * @since 2020/11/1 3:55:20
 */
@Slf4j
public class CompositeFilterMain {
    
    public static void main(String[] args) {
        log.debug("debug");
        log.debug("debug bgm");
        log.info("info");
        log.info("info bgm");
        log.warn("warn");
        log.warn("warn bgm");
        log.error("error");
        log.error("error bgm");
    }
}
