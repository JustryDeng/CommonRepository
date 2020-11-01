package com.pingan.log4j2filter.test;

import com.pingan.log4j2filter.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试使用 LevelMatchFilter
 *
 * @author {@link JustryDeng}
 * @since 2020/11/1 3:55:20
 */
@Slf4j
public class LevelMatchFilterMain {
    
    public static void main(String[] args) {
        log.debug("i am debug");
        log.info("i am info");
        log.warn("i am warn");
        log.error("i am error");
    }
}