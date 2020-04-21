package com.aspire.plus.model;

import com.aspire.plus.enums.AgeEnum;
import com.aspire.plus.enums.NameEnum;
import com.aspire.plus.validator.annotation.EnumConstraint;
import lombok.Data;

/**
 * 学生类(用于测试{@link EnumConstraint})
 *
 * @author JustryDeng
 * @date 2020/4/21 11:42:44
 */
@Data
public class Student {

    @EnumConstraint(enumClass = NameEnum.class, method = "getName", message = "名字不合法non-one")
    private String name;

    @EnumConstraint(enumClass = AgeEnum.class, method = "getAge", message = "年龄不合法non-one")
    private Integer age;

    private String motto;
}
