package com.szlaozicl.demo.annotation;


import com.szlaozicl.demo.author.JustryDeng;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 控制是否参与Gson序列化、反序列化
 *
 * @author {@link JustryDeng}
 * @date 2020/6/1 23:48:02
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GsonIgnore {
    
    /** 不参与序列化 */
    boolean serialize() default true;
    
    /** 不参与反序列化 */
    boolean deserialize() default true;
}
