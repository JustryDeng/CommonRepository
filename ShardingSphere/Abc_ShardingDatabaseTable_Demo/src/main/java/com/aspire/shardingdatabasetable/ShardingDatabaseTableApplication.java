package com.aspire.shardingdatabasetable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 *
 * @author JustryDeng
 * @date 2019/5/29 11:03
 */
@SpringBootApplication
@EnableTransactionManagement
public class ShardingDatabaseTableApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingDatabaseTableApplication.class, args);
    }

}
