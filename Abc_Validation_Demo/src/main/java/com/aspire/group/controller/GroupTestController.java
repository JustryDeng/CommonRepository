package com.aspire.group.controller;

import com.aspire.group.anno.Create;
import com.aspire.group.anno.Update;
import com.aspire.group.model.Student;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.groups.Default;

/**
 * 测试 校验注解中， group属性的使用
 *
 * @author JustryDeng
 * @date 2020/4/22 22:21:29
 */
@RestController
public class GroupTestController {

    @PostMapping(value = "/group/test/one")
    public String testOne(@Validated(value = Create.class) @RequestBody Student student) {
        System.out.println(student);
        return "group-test-one pass!";
    }

    @PostMapping(value = "/group/test/two")
    public String testTwo(@Validated(value = Update.class) @RequestBody Student student) {
        System.out.println(student);
        return "group-test-two pass!";
    }

    @PostMapping(value = "/group/test/three")
    public String testThree(@Validated(value = {Create.class, Update.class}) @RequestBody Student student) {
        System.out.println(student);
        return "group-test-three pass!";
    }

    @PostMapping(value = "/group/test/four")
    public String testFour(@Validated(value = Default.class) @RequestBody Student student) {
        System.out.println(student);
        return "group-test-four pass!";
    }

    @PostMapping(value = "/group/test/five")
    public String testFive(@Validated @RequestBody Student student) {
        System.out.println(student);
        return "group-test-five pass!";
    }
}
