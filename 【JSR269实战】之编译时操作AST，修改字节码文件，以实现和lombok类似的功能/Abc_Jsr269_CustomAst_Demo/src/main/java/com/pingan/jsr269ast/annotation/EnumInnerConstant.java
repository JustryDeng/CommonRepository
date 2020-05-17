package com.pingan.jsr269ast.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 生成一个公开的静态内部类， 这个内部类中持有了外部类的public-static-final常量
 *
 * @author JustryDeng
 * @date 2020/5/13 20:50:51
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface EnumInnerConstant {

    /** 默认的内部类名 */
    String innerClassName() default "JustryDeng";
}
