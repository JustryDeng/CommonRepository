package com.aspire.plus.controller;

import com.aspire.plus.enums.AgeEnum;
import com.aspire.plus.enums.NameEnum;
import com.aspire.plus.model.Student;
import com.aspire.plus.validator.annotation.EnumConstraint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * {@link EnumConstraint}测试
 *
 * @author JustryDeng
 * @date 2019/1/18 22:22
 */
@Slf4j
@Validated
@RestController
public class EnumController {

    @RequestMapping(value = "/enum/test/one", method = {RequestMethod.GET, RequestMethod.POST})
    public String validationOne(
            @EnumConstraint(enumClass = NameEnum.class, method = "getName", message = "名字不合法one") String name,
            @EnumConstraint(enumClass = AgeEnum.class, method = "getAge", message = "年龄不合法one") Integer age
            ) {
        log.info(name + "\t" + age);
        return "one pass!";
    }

    @RequestMapping(value = "/enum/test/two", method = {RequestMethod.GET, RequestMethod.POST})
    public String validationTwo(@Validated Student student) {
        log.info(student.toString());
        return "two pass!";
    }

    @PostMapping(value = "/enum/test/three")
    public String validationThree(@Validated @RequestBody Student student) {
        log.info(student.toString());
        return "three pass!";
    }

}