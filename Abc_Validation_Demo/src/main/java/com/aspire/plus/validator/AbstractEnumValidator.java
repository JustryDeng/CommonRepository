package com.aspire.plus.validator;

import com.aspire.plus.validator.annotation.EnumConstraint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.validator.internal.engine.ValidationContext;
import org.hibernate.validator.internal.engine.ValueContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * EnumConstraint注解 校验器 实现
 *
 * 注:验证器需要实现ConstraintValidator<U, V>, 其中 U为对应的注解类， V为被该注解标记的字段的类型(或其父类型)
 *
 * 注: 当项目启动后，会(懒加载)创建ConstraintValidator实例，在创建实例后会初始化调
 *     用{@link ConstraintValidator#initialize}方法。
 *     所以， 只有在第一次请求时，会走initialize方法， 后面的请求是不会走initialize方法的。
 *
 * 注: (懒加载)创建ConstraintValidator实例时， 会走缓存; 如果缓存中有，则直接使用相
 *     同的ConstraintValidator实例； 如果缓存中没有，那么会创建新的ConstraintValidator实例。
 *     由于缓存的key是能唯一定位的， 且 ConstraintValidator的实例属性只有在
 *     {@link ConstraintValidator#initialize}方法中才会写；在{@link ConstraintValidator#isValid}
 *     方法中只是读。
 *     所以不用担心线程安全问题。
 *
 * 注: 如何创建ConstraintValidator实例的，可详见源码
 *     @see ConstraintTree#getInitializedConstraintValidator(ValidationContext, ValueContext)
 *
 *
 * @author JustryDeng
 * @date 2019/1/15 1:19
 */
@Slf4j
@SuppressWarnings("rawtypes")
public abstract class AbstractEnumValidator<T> implements ConstraintValidator<EnumConstraint, T> {

    /** 注解里指定的枚举类 */
    Class<? extends Enum> clazz;

    /** 注解里指定的方法 */
    String method;

    /** 注解里指定的错误提示 */
    String message;

    /** method对应的Method实例 */
    Method enumMethod;

    /** clazz中的枚举项 */
    Enum[] enumConstants;

    @Override
    public void initialize(EnumConstraint enumConstraint) {
        clazz = enumConstraint.enumClass();
        method = enumConstraint.method();
        message = enumConstraint.message();
        String enumClassName = clazz.getName();
        try {
            enumMethod = clazz.getMethod(this.method);
        } catch (Exception e) {
            throw new RuntimeException(String.format("cannot get method[%s] from enumClass[%s]",
                    method, enumClassName), e);
        }
        enumConstants = clazz.getEnumConstants();
    }

    @Override
    public boolean isValid(T targetVale, ConstraintValidatorContext context) {
        if (targetVale == null) {
            log.warn(" targetVale == null, isValid(...) return false");
            return false;
        }
        if (ArrayUtils.isEmpty(enumConstants)) {
            log.warn(" valueCache is empty, isValid(...) return false");
            // 空的枚举, 直接返回false
            return false;
        }
        return pass(targetVale);
    }

    /**
     * 校验是否通过
     *
     * @param targetVale
     *            要校验的枚举值
     *
     * @return  校验是否通过
     * @date 2020/4/21 11:34:09
     */
    protected abstract boolean pass (T targetVale);
}