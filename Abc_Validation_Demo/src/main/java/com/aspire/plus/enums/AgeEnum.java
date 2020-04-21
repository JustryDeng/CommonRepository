package com.aspire.plus.enums;

import lombok.Getter;

/**
 * (non-javadoc)
 *
 * @author JustryDeng
 * @date 2020/4/21 10:08:26
 */
@Getter
public enum AgeEnum {

    /** 常量字段 */
    TEN(10, "十岁了"),

    EIGHTEEN(18, "十八岁了"),

    TWENTY(20, "二十岁了");

    private Integer age;

    private String description;

    AgeEnum(int age, String description) {
        this.age = age;
        this.description = description;
    }
}
