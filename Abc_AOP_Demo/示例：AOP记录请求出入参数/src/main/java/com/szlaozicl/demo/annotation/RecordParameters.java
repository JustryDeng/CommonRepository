package com.szlaozicl.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开关注解 - 记录 入参、出参
 *
 * @author JustryDeng
 * @date 2019/12/4 13:53
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface RecordParameters {
}
