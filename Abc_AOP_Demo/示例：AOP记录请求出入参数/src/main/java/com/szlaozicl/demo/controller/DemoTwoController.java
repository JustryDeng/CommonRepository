package com.szlaozicl.demo.controller;


import com.szlaozicl.demo.annotation.IgnoreRecordParameters;
import com.szlaozicl.demo.annotation.RecordParameters;
import com.szlaozicl.demo.model.EmployeePO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于测试的controller层
 *
 * @author JustryDeng
 * @date 2019/11/26 10:05
 */
@Slf4j
@RestController
@SuppressWarnings("unused")
public class DemoTwoController {

    private static SecureRandom RANDOM = new SecureRandom();

    @GetMapping("/insert2")
    @RecordParameters(logLevel = RecordParameters.LogLevel.DEBUG, strategy = RecordParameters.Strategy.OUTPUT)
    public Integer insertDemo(String name, Integer age, String motto, String hobby) {

        return RANDOM.nextInt(10);
    }

    @GetMapping("/delete2")
    @RecordParameters
    @IgnoreRecordParameters
    public Boolean deleteDemo(Integer id) {
        return RANDOM.nextBoolean();
    }

    @GetMapping("/update2")
    @RecordParameters
    @IgnoreRecordParameters
    public Boolean updateDemo(String hobby, Integer id) {
        return RANDOM.nextBoolean();
    }

    @GetMapping("/select2")
    @RecordParameters(logLevel = RecordParameters.LogLevel.DEBUG, strategy = RecordParameters.Strategy.INPUT_OUTPUT)
    public List<EmployeePO> selectDemo(String namePrefix) {
        List<EmployeePO> list = new ArrayList<>(2);
        list.add(EmployeePO.builder().name("张三").age(18).build());
        list.add(EmployeePO.builder().name("李四").age(82).build());
        return list;
    }

    @GetMapping("/num2")
    public Integer[] numTest(Integer... num) {
        return num;
    }

    @GetMapping("/no/args2")
    public String noArgs() {
        return "/no/args2";
    }

    @GetMapping("/no/return2")
    public void noReturn() {
    }
}
