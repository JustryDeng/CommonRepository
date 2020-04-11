package com.szlaozicl.demo.controller;


import com.szlaozicl.demo.annotation.IgnoreRecordParameters;
import com.szlaozicl.demo.annotation.RecordParameters;
import com.szlaozicl.demo.model.EmployeePO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 用于测试的controller层
 *
 * @author JustryDeng
 * @date 2019/11/26 10:05
 */
@Slf4j
@RestController
@SuppressWarnings("unused")
@RecordParameters(logLevel = RecordParameters.LogLevel.WARN, strategy = RecordParameters.Strategy.INPUT_OUTPUT)
public class DemoOneController {

    @GetMapping("/insert1")
    public Integer insertDemo(String name, Integer age, String motto, String hobby) {
        return ThreadLocalRandom.current().nextInt(10);
    }

    @GetMapping("/delete1")
    @IgnoreRecordParameters
    public Boolean deleteDemo(Integer id) {
        return ThreadLocalRandom.current().nextBoolean();
    }

    @GetMapping("/update1")
    public Boolean updateDemo(String hobby, Integer id) {
        return ThreadLocalRandom.current().nextBoolean();
    }

    @GetMapping("/select1")
    @IgnoreRecordParameters
    public List<EmployeePO> selectDemo(String namePrefix) {
        List<EmployeePO> list = new ArrayList<>(2);
        list.add(EmployeePO.builder().name("张三").age(18).build());
        list.add(EmployeePO.builder().name("李四").age(82).build());
        return list;
    }

    @GetMapping("/num1")
    public Integer[] numTest(Integer... num) {
        return num;
    }

    @GetMapping("/no/args1")
    public String noArgs() {
        return "/no/args";
    }

    @GetMapping("/no/return1")
    public void noReturn() {
    }
}
