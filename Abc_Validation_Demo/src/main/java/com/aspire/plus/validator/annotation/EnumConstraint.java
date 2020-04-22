package com.aspire.plus.validator.annotation;

import com.aspire.plus.enums.AgeEnum;
import com.aspire.plus.validator.impl.StringEnumValidator;
import com.aspire.plus.validator.impl.IntegerEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 枚举约束
 *
 * 注:当没有指定默认值时，那么在使用此注解时，就必须输入对应的属性值
 *
 * @author JustryDeng
 * @date 2020/4/21 10:13:55
 */
@Retention(RUNTIME)
@Target({FIELD, PARAMETER})
@Constraint(validatedBy = {StringEnumValidator.class, IntegerEnumValidator.class})
public @interface EnumConstraint {

    /** 枚举类 */
    @SuppressWarnings({"rawtypes"})
    Class<? extends Enum> enumClass();

    /** 方法(用这个方法来定位枚举中的字段值) */
    String method();

    /** 提示 */
    String message() default "param is not a enum value";

    /** 约束注解在验证时所属的组别 */
    Class<?>[] groups() default { };

    /** 负载 */
    Class<? extends Payload>[] payload() default { };

}