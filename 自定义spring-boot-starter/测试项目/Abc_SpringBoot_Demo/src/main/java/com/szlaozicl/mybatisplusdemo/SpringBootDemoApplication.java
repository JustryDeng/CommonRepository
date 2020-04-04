package com.szlaozicl.mybatisplusdemo;

import com.pingan.customstarter.service.LogicHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * (non-javadoc)
 *
 * @author JustryDeng
 * @date 2020/4/4 19:47:38
 */
@SpringBootApplication
@RequiredArgsConstructor
public class SpringBootDemoApplication implements ApplicationRunner {

    private final LogicHandler logicHandler;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        logicHandler.handle();
    }
}
