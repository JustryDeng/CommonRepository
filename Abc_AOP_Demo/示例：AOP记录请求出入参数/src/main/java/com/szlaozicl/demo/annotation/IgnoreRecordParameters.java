package com.szlaozicl.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开关注解 - 忽略RecordParameters注解的功能
 *
 * @author JustryDeng
 * @date 2019/12/4 13:53
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface IgnoreRecordParameters {
}
