package com.aspire.controller;

import com.aspire.constraints.anno.ConstraintsJustryDeng;
import com.aspire.model.User;
import com.aspire.model.ValidationBeanModel;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMax;
import java.util.List;

/**
 * Controller层 --- 初步简单测试 @Validated 的使用位置
 *
 * 对比测试过程:
 *    方案一 : 不在类上加@Validated注解，访问这六个接口
 *    方案二 : 在类上加@Validated注解，再次访问这六个接口
 *
 *    对比方案一和方案二，可初步得出@Validated的使用时机:
 *        1.当我们是在模型里面对模型字段添加约束注解，在Controller中使用模型接收数
 *          据时，@Validated要直接放在该模型参数前才有效。 如: "/test/one"
 *        2.当我们是直接在Controller层中的参数前，使用约束注解时，@Validated要直接放在类上，
 *          才会有效。如: /test/six
 *
 *
 * @author JustryDeng
 * @date 2019/1/18 22:22
 */
@RestController
@Validated
public class JustryDengController {


    @RequestMapping(value = "/test/one", method = RequestMethod.GET)
    public String validationOne(@Validated ValidationBeanModel.AbcDecimalMax myDecimalMax) {
        System.out.println(myDecimalMax.getMyDecimalMax());
        return "one pass!";
    }

    @RequestMapping(value = "/test/two", method = RequestMethod.GET)
    @Validated
    public String validationTwo(ValidationBeanModel.AbcDecimalMax myDecimalMax) {
        System.out.println(myDecimalMax.getMyDecimalMax());
        return "two pass!";
    }

    @RequestMapping(value = "/test/three", method = RequestMethod.GET)
    public String validationThree(ValidationBeanModel.AbcDecimalMax myDecimalMax) {
        System.out.println(myDecimalMax.getMyDecimalMax());
        return "three pass!";
    }

    @RequestMapping(value = "/test/four", method = RequestMethod.GET)
    public String validationFour(@Validated  @DecimalMax(value = "12.3") String myDecimalMax) {
        System.out.println(myDecimalMax);
        return "four pass!";
    }

    @RequestMapping(value = "/test/five", method = RequestMethod.GET)
    @Validated
    public String validationFive(@DecimalMax(value = "12.3") String myDecimalMax) {
        System.out.println(myDecimalMax);
        return "five pass!";
    }

    @RequestMapping(value = "/test/six", method = RequestMethod.GET)
    @Validated
    public String validationSix(@DecimalMax(value = "12.3") String myDecimalMax) {
        System.out.println(myDecimalMax);
        return "six pass!";
    }

    @RequestMapping(value = "/test/seven", method = RequestMethod.GET)
    public String validationSeven(@ConstraintsJustryDeng(contains = "JustryDeng是一个大帅哥") String str) {
        System.out.println(str);
        return "seven pass!";
    }

    @RequestMapping(value = "/test/eight", method = RequestMethod.POST)
    public String validationEight(@Validated @RequestBody User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> errorsList = bindingResult.getAllErrors();
            for (ObjectError oe : errorsList) {
                System.err.println(oe.getDefaultMessage());
            }
        }
        System.out.println(user);
        return "eight pass!";
    }

    @RequestMapping(value = "/test/nine", method = RequestMethod.POST)
    public String validationNine(@Validated @RequestBody User user) {
        System.out.println(user);
        return "nine pass!";
    }
}