package com.aspire.constraints.impl;

import com.aspire.constraints.anno.ConstraintsJustryDeng;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ConstraintsJustryDeng注解 校验器 实现
 * <p>
 * 注:验证器需要实现ConstraintValidator<U, V>, 其中 U为对应的注解类， V为该注解的@Target指向的类型
 *
 * @author JustryDeng
 * @date 2019/1/15 1:19
 */
public class JustryDengValidator implements ConstraintValidator<ConstraintsJustryDeng, Object> {

    /** 错误提示信息 */
    private String contains;

    /**
     * 初始化方法， 在执行isValid 方法前，会先执行此方法
     *
     * @param constraintAnnotation
     *         注解信息模型，可以从该模型中获取注解类中定义的一些信息，如默认值等
     * @date 2019/1/19 11:27
     */
    @Override
    public void initialize(ConstraintsJustryDeng constraintAnnotation) {
        System.out.println(constraintAnnotation.message());
        this.contains = constraintAnnotation.contains();
    }

    /**
     * 校验的具体逻辑实现
     * <p>
     * 注: 此方法可能会并发执行，需要根据实际情况看否是需要保证 线程安全
     *
     * @param value
     *         被自定义注解所标注的对象的 值
     * @param context
     *         Provides contextual data and operation when applying a given constraint validator.
     * @return 校验是否通过
     * @date 2019/1/19 11:30
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (value instanceof String) {
            String strMessgae = (String) value;
            return strMessgae.contains(contains);
        } else if (value instanceof Integer) {
            return contains.contains(String.valueOf(value));
        }
        return false;
    }

}