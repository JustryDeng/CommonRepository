package com.aspire.controller;

import com.aspire.model.Employee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 控制层
 *
 * @author JustryDeng
 * @date 2019/1/8 18:53
 */
@RestController
@Api(tags = {"归类API"})
public class JustryDengController {

    /**
     * 简单获取Employee
     *
     *
     * @return Employee模型
     *
     * @date 2019/1/9 0:36
     */
    @GetMapping("/get/single/employee")
    @ApiOperation(value = "我是value属性值", notes = "我是notes属性值", tags = {"归类API2"})
    public Employee getEmployee() {
        Employee employee = new Employee();
        employee.setName("张三123");
        employee.setAge(18);
        employee.setGender("男");
        employee.setId(9527);
        return employee;
    }

    /**
     * 根据Employee模型获取toString信息
     *
     * @param employee
     *            employee模型
     * @return  模型toString结果
     * @date 2019/1/9 0:38
     */
    @ApiIgnore
    @PostMapping("/get/employee/info")
    public String getEmployeeInfo(@RequestBody Employee employee) {
        return employee.toString();
    }

    /**
     * 获取随机数
     *
     * @param basenumber
     *            基数
     * @return 随机数
     * @date 2019/1/9 0:44
     */
    @GetMapping("/get/random/number")
    public Integer geteRandomNumber(@RequestParam("basenumber") Integer basenumber){
        return basenumber + new Random().nextInt(100);
    }

    /**
     * 获取随机数
     *
     * @return Employee集合
     * @date 2019/1/9 0:44
     */
    @PostMapping("/get/employee/list")
    public List<Employee> geteEmployeeList(){
        List<Employee> list = new ArrayList<>(4);
        Employee employeeOne = new Employee();
        employeeOne.setName("JustryDeng");
        employeeOne.setAge(18);
        employeeOne.setGender("男");
        employeeOne.setId(9527);
        list.add(employeeOne);
        Employee employeeTwo = new Employee();
        employeeTwo.setName("邓沙利文");
        employeeTwo.setAge(24);
        employeeTwo.setGender("男");
        employeeTwo.setId(8080);
        list.add(employeeTwo);
        return list;
    }

}