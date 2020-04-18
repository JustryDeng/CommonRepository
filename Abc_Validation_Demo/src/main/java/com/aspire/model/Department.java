package com.aspire.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 部门
 *
 * @author JustryDeng
 * @date 2020/4/18 13:49:37
 */
@Data
public class Department {

    @Min(value = 1, message = "部门id不能小于1")
    private Integer id;

    /** 部门名 */
    @NotEmpty(message = "部门名不能为空")
    private String name;

    /** 部门邮件地址 */
    @Email(message = "部门邮件地址不合法")
    private String email;

    /** 所属公司 */
    @Valid
    private Company company;

    /** 员工 */
    @Valid
    private List<Employee> employeeList;
}
