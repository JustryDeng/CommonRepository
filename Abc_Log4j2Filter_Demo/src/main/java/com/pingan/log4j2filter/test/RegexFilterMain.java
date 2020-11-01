package com.pingan.log4j2filter.test;

import com.pingan.log4j2filter.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试使用 RegexFilter
 *
 * @author {@link JustryDeng}
 * @since 2020/11/1 3:55:20
 */
@Slf4j
public class RegexFilterMain {
    
    public static void main(String[] args) {
        log.info("a");
        log.info("aa");
        log.info("aaa");
        log.info("aaaa");
        log.info("aaaaa");
        log.info("aaaaaa");
    }
}

