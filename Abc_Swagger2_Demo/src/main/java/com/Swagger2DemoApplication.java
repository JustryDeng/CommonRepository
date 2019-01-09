package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动类
 *
 * 注:顾名思义 @EnableSwagger2 表示启用Swagger2
 *
 * @author JustryDeng
 * @date 2019/1/9 11:21
 */
@SpringBootApplication
@EnableSwagger2
public class Swagger2DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Swagger2DemoApplication.class, args);
    }

}

