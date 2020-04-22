package com.aspire.plus.validator.impl;

import com.aspire.plus.validator.AbstractEnumValidator;
import com.aspire.plus.validator.annotation.EnumConstraint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Integer的枚举校验实现
 *
 * @author JustryDeng
 * @date 2020/4/21 10:36:04
 */
@Slf4j
@SuppressWarnings("rawtypes")
public class IntegerEnumValidator extends AbstractEnumValidator<Integer> {

    /** 只在懒加载IntegerEnumValidator后的initialize里写， 其它地方只读 */
    private Set<Integer> valueCache = new CopyOnWriteArraySet<>();

    @Override
    public void initialize(EnumConstraint enumConstraint) {
        super.initialize(enumConstraint);
        if (ArrayUtils.isEmpty(enumConstants)) {
            log.warn(" enumConstants is empty, so valueCache is empty!");
            return;
        }
        boolean accessible = enumMethod.isAccessible();
        if (!accessible) {
            enumMethod.setAccessible(true);
        }
        try {
            // 参数类型(这里为Integer) 是否与 枚举(的对应字段)值类型 匹配
            Object firstEnumValue = enumMethod.invoke(enumConstants[0]);
            if (!(firstEnumValue instanceof Integer)) {
                throw new RuntimeException(String.format("param-type[%s] and enumValue-type[%s] Cannot match",
                        Integer.class.getTypeName(), firstEnumValue.getClass().getTypeName()));
            }
            for (Enum enumItem : enumConstants) {
                valueCache.add((Integer)enumMethod.invoke(enumItem));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (!accessible) {
                enumMethod.setAccessible(false);
            }
        }
    }

    @Override
    protected boolean pass(Integer targetVale) {
        return valueCache.contains(targetVale);
    }
}
