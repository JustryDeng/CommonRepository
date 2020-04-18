package com.aspire.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 公司
 *
 * @author JustryDeng
 * @date 2020/4/18 13:49:37
 */
@Data
public class Company {

    /** 公司名称 */
    @NotEmpty(message = "公司名称不能为空")
    private String name;
}
