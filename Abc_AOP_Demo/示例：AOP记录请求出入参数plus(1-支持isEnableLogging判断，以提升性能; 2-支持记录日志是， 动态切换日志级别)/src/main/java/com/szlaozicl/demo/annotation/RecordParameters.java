package com.szlaozicl.demo.annotation;

import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记录 入参、出参
 *
 * @author JustryDeng
 * @date 2019/12/4 13:53
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface RecordParameters {

    /** 日志级别 */
    LogLevel logLevel() default LogLevel.INFO;

    /** 日志策略 */
    Strategy strategy() default Strategy.INPUT_OUTPUT;

    /**
     * 日志级别枚举
     *
     * P.S. 对于入参、出参日志来讲，debug、info、warn这三种级别就够了
     */
    @Getter
    enum LogLevel {

        /** debug */
        DEBUG(1),

        /** info */
        INFO(2),

        /** warn */
        WARN(3);
    
        /** 用于快速switch的辅助属性 */
        private int fastEnum;
        
        LogLevel(int fastEnum) {
            this.fastEnum = fastEnum;
        }
    }

    /**
     * 日志记录策略枚举
     */
    enum Strategy {

        /** 记录入参 */
        INPUT,

        /** 记录出参 */
        OUTPUT,

        /** 既记录入参，也记录出参 */
        INPUT_OUTPUT
    }
}
