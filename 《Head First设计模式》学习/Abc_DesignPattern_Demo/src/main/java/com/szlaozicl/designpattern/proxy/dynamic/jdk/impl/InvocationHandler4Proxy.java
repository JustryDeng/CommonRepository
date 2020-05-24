package com.szlaozicl.designpattern.proxy.dynamic.jdk.impl;

import com.szlaozicl.designpattern.author.JustryDeng;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 生成的代理类会将具体的逻辑委托给 InvocationHandler#invoke进行
 *
 * @author {@link JustryDeng}
 * @date 2020/5/24 9:36:06
 */
public class InvocationHandler4Proxy implements InvocationHandler {

    /** 被代理对象 */
    private Object target;

    private InvocationHandler4Proxy(Object target) {
        this.target = target;
    }

    /**
     * 代理对象会委托此方法进行真正的逻辑处理
     *
     * @param proxy
     *            代理对象
     * @param method
     *            要执行的方法(即: 本次被代理的方法)
     * @param args
     *            method的参数
     * @return  结果
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 前切入逻辑
        System.err.println(" 前置工作~");
        // 调用源对象的方法
        Object result = method.invoke(target, args);
        // 后切入逻辑
        System.err.println(" 收尾工作~");
        return result;
    }

    /**
     * 获取到代理对象
     * 注: 在程序执行时, 代理对象会将具体的逻辑委托给InvocationHandler#invoke进行
     *
     * @param target
     *            被代理对象
     *
     * @return 代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProxyInstance(Object target) {
        InvocationHandler4Proxy invocationHandler = new InvocationHandler4Proxy(target);
        Class<?> targetClass = target.getClass();
        // 通过被代理对象target和InvocationHandler实例, 获取到代理对象
        return (T) Proxy.newProxyInstance(targetClass.getClassLoader(), targetClass.getInterfaces(), invocationHandler);
    }
}
