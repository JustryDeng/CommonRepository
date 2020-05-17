package com.szlaozicl.mybatisplusdemo.model;

import com.pingan.jsr269ast.annotation.EnumInnerConstant;

/**
 * 枚举类
 *
 * @author JustryDeng
 * @date 2020/5/17 11:04:21
 */
@SuppressWarnings("unused")
@EnumInnerConstant(innerClassName = "HelloWorld")
public enum PersonStatusEnum {

    /** 枚举项 */
    EAT(1, "吃"),
    DRINK(2, "喝"),
    PLAY(3, "玩"),
    ENJOY(4, "乐");

    private int status;

    private String description;

    PersonStatusEnum(int status, String description) {
        this.status  = status;
        this.description  = description;
    }

}
