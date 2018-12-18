package com.aspire.annotation;

import java.lang.annotation.*;

/**
 * 标志性注解
 *
 * @author JustryDeng
 * @date 2018/12/18 14:41
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdviceOne {
}
