package com.aspire.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 员工
 *
 * @author JustryDeng
 * @date 2020/4/18 13:51:42
 */
@Data
public class Employee {

    /** 员工姓名 */
    @NotEmpty(message = "员工姓名不能为空")
    private String name;

    /** 员工薪资CNY */
    @NotNull(message = "员工薪资不能为空")
    @Min(value = 1000, message = "员工薪资不能低于1000")
    private Long salary;
}
