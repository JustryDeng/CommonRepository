package com.szlaozicl.demo.controller;


import com.szlaozicl.demo.annotation.IgnoreRecordParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 用于execution与注解一起使用的情况
 *
 * @author JustryDeng
 * @date 2019/11/26 10:05
 */
@Slf4j
@RestController
public class DemoFourController {

    @GetMapping("/insert4")
    public Integer insertDemo(String name, Integer age, String motto, String hobby) {
        return ThreadLocalRandom.current().nextInt(10);
    }

    @GetMapping("/delete4")
    @IgnoreRecordParameters
    public Boolean deleteDemo(Integer id) {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
