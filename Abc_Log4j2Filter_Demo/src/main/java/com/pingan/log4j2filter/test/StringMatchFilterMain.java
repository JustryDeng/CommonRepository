package com.pingan.log4j2filter.test;

import com.pingan.log4j2filter.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试使用 StringMatchFilter
 *
 * @author {@link JustryDeng}
 * @since 2020/11/1 3:55:20
 */
@Slf4j
public class StringMatchFilterMain {
    
    public static void main(String[] args) {
        log.info("info abc");
        log.info("info bgm ");
        log.info("info xyz");
        log.info("info miss");
    }
}

