package com.aspire.ssm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * 注:EnableTransactionManagement注解貌似没什么用， 加不加都能开启事务
 *
 * @author JustryDeng
 * @date 2019/6/21 16:41
 */
@SpringBootApplication
public class SsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsmApplication.class, args);
    }

}
