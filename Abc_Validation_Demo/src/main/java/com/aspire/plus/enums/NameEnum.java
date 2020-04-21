package com.aspire.plus.enums;

import lombok.Getter;

/**
 * (non-javadoc)
 *
 * @author JustryDeng
 * @date 2020/4/21 10:08:26
 */
@Getter
public enum NameEnum {

    /** 常量字段 */
    ZHANG_SAN("张三", "十岁了"),

    LI_SI("李四", "十八岁了"),

    WANG_WU("王五", "二十岁了");

    private String name;

    private String description;

    NameEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
