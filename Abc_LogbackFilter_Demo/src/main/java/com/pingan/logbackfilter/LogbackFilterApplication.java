package com.pingan.logbackfilter;

import com.pingan.logbackfilter.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author {@link JustryDeng}
 * @since 2020/8/18 10:11:02
 */
@Slf4j
@SpringBootApplication
public class LogbackFilterApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(LogbackFilterApplication.class, args);
    }
    
}
