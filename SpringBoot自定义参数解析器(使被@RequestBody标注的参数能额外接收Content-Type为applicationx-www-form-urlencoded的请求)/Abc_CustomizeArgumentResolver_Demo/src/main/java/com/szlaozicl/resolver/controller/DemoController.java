package com.szlaozicl.resolver.controller;

import com.szlaozicl.resolver.model.Person;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制层
 *
 * @author JustryDeng
 * @date 2019/8/18 2:02
 */
@RestController
public class DemoController {

    /**
     * 基本测试: 测试application/x-www-form-urlencoded
     */
    @PostMapping("/xyz")
    public Person applicationFormUrlencoded(Person person) {
        return person;
    }

    /**
     * 1、基本测试:
     *       测试Content-Type为application/json
     *
     * 2、自定义参数解析器测试:
     *       测试Content-Type为application/x-www-form-urlencoded
     */
    @PostMapping("/abc")
    public Person applicationJsonTest(@RequestBody Person person) {
        return person;
    }

    /**
     * 其它测试
     */
    @PostMapping("/foo")
    public Person otherTest(@RequestBody Person person, @RequestParam("test") String test) {
        System.out.println(test);
        return person;
    }
}