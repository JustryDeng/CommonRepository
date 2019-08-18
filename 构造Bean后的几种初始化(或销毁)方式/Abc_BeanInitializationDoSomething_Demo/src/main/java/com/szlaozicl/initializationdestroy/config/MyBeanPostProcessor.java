package com.szlaozicl.initializationdestroy.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 自定义BeanPostProcessor
 *
 * 注:之所以不直接用Abc实现BeanPostProcessor接口，是因为:如果用Abc实现BeanPostProcessor接口的话，
 *    其注册进SpringIOC容器的处理器的postProcessBeforeInitialization方法和
 *    postProcessAfterInitialization方法，只会对在Abc后面才注册进SpringIOC容器的Bean起效果，对
 *    于Abc本身，是没有效果的。
 *
 * @author JustryDeng
 * @date 2019/8/18 14:43
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (Abc.class.equals(bean.getClass())) {
            System.out.println("BeanPostProcessor接口的postProcessBeforeInitialization方法");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (Abc.class.equals(bean.getClass())) {
            System.out.println("BeanPostProcessor接口的postProcessAfterInitialization方法");
        }
        return bean;
    }
}